package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserModelDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
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

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    public User getUserById(UUID id) throws UsernameNotFoundException {
        User user = userRepository.getUserById(id);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return user;
    }

    public User save(User user){
        return this.userRepository.save(user);
    }
}
