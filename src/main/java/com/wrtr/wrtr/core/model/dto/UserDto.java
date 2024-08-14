package com.wrtr.wrtr.core.model.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * The DTO class for the User objects
 */
public class UserDto {
    private String username;
    private String bio;
    private MultipartFile profilePicture;

    /**
     * Empty constructor
     */
    public UserDto() {}

    /**
     * The complete constructor
     * @param username User's username
     * @param bio User's bio
     * @param profilePicture User's profile picture
     */
    public UserDto(String username, String bio, MultipartFile profilePicture){
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    /**
     * Get user's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set user's username
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get user's bio
     * @return User's bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set user's new bio
     * @param bio User's bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Get the profile picture file
     * @return Profile picture file
     */
    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    /**
     * Set the user's profile picture
     * @param profilePicture New profile picture file
     */
    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }
}
