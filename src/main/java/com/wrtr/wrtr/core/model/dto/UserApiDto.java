package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;

import java.util.UUID;

/**
 * A record that contains all the frontend-facing data of a user
 * @param id ID of the user
 * @param username Username
 * @param bio User's bio
 * @param profilePicture Path to the user's profile picture
 * @param role User's role
 */
public record UserApiDto(UUID id, String username, String bio, String profilePicture, String role) {
    /**
     * Get a UserApiDto object from a User object
     * @param user User
     * @return UserApiDto based on it
     */
    public static UserApiDto fromUser(User user) {
        Resource profilePicture = user.getProfilePicture();
        return new UserApiDto(user.getId(), user.getUsername(), user.getBio(),
                profilePicture == null ? "static/images/emptypfp.jpg" : profilePicture.getPath(),
                user.getRole());
    }
}
