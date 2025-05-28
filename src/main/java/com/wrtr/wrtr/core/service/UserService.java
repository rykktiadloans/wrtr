package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.builders.UserBuilderFactory;
import com.wrtr.wrtr.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The service that gets user's details
 */
@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBuilderFactory userBuilderFactory;
    @Autowired
    private Environment environment;

    /**
     * Method that allows to get the user details of the user
     * @param username User's username
     * @return The user's details
     * @throws UsernameNotFoundException Thrown if the user with this name does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userRepository.getUserByEmail(username);
        if(userModel == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserModelDetails(userModel);
    }

    /**
     * Returns a user with a matching email
     * @param email User's email
     * @return Matching user
     * @throws UsernameNotFoundException Thrown if the user was not found
     */
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    /**
     * Returns user with a matching ID
     * @param id User's ID
     * @return Matching user
     * @throws UsernameNotFoundException Thrown if the user was not found
     */
    public User getUserById(UUID id) throws UsernameNotFoundException {
        User user = userRepository.getUserById(id);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    /**
     * Fetches the authenticated user
     * @param authentication Authentication object
     * @return The corresponding user
     * @throws UsernameNotFoundException Thrown if the user is not found
     */
    public User getUserByAuth(Authentication authentication) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(authentication.getName());
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    /**
     * Modify and <em>save</em> the users so that the follower user now follows the following user
     * @param follower The user that is going to be a follower
     * @param following The user that the follower wants to follow
     */
    public void addFollower(User follower, User following) {
        follower.getFollowing().add(following);
        following.getFollowers().add(follower);
        this.save(following);
    }

    /**
     * Modify and <em>save</em> the users to that the follower user no longer follows the following user
     * @param follower The user that doesn't want to follow the following user
     * @param following The user that follower used to follow
     */
    public void removeFollower(User follower, User following) {
        follower.getFollowing().remove(following);
        following.getFollowers().remove(follower);
        this.save(following);
    }

    /**
     * Checks whether the user is the same as the one logged in
     * @param authentication Authentication object
     * @param user User object
     * @return True if they are the same user, false otherwise
     */
    public boolean isUsersPage(Authentication authentication, User user){
        String correctUser = authentication == null ? "" : authentication.getName();
        return Objects.equals(correctUser, user.getEmail());
    }

    /**
     * Save user to the database
     * @param user User to save
     * @return The user
     */
    public User save(User user){
        return this.userRepository.save(user);
    }

    /**
     * Returns a list of users whose usernames contain a supplied string
     * @param matchBy String to match by
     * @return List of matched users
     */
    public List<User> searchUsersWithSimilarUsername(String matchBy){
        return this.userRepository.searchUsersWithSimilarUsername(matchBy);
    }

    /**
     * Makes sure that the admin user, as per the environment variables, exists
     */
    @Transactional
    public void ensureAdminUserExists() {
        String adminEmail = this.environment.getProperty("wrtr.admin.email");
        User admin = this.userRepository.getUserByEmail(adminEmail);
        if(admin == null) {
            Logger logger = LoggerFactory.getLogger(UserService.class);
            int deleted = this.userRepository.cleanUsersByRole("admin");
            String message = "Admin accounts deleted: " + deleted;
            logger.debug(message);
            admin = this.userBuilderFactory.createUserBuilder()
                    .addUsername(this.environment.getProperty("wrtr.admin.username"))
                    .addEmail(adminEmail)
                    .addPassword(this.environment.getProperty("wrtr.admin.password"))
                    .addRole("admin")
                    .build();
            this.userRepository.save(admin);
        }

    }
}
