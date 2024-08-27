package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The service that gets user's details
 */
@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Method that allows to get the user details of the user
     * @param username User's username
     * @return The user's details
     * @throws UsernameNotFoundException Thrown if the user with this name does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userRepository.getUserByUsername(username);
        if(userModel == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserModelDetails(userModel);
    }

    /**
     * Returns a user with a matching username
     * @param username User's username
     * @return Matching user
     * @throws UsernameNotFoundException Thrown if the user was not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
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
        User user = userRepository.getUserByUsername(authentication.getName());
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    /**
     * Save user to the database
     * @param user User to save
     * @return The user
     */
    public User save(User user){
        return this.userRepository.save(user);
    }
}
