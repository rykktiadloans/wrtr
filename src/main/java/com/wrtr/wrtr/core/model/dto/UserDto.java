package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * Checks if the username is too large
     * @return True if it's too large, false otherwise
     */
    public boolean isUsernameTooLarge(){
        return this.getUsername().length() > User.USERNAME_SIZE;
    }

    /**
     * Checks if the bio is too large
     * @return True if it's too large, false otherwise
     */
    public boolean isBioTooLarge(){
        return this.getBio().length() > User.BIO_SIZE;
    }

    /**
     * Checks if the profile picture was supplied
     * @return Returns true if picture was supplied, false otherwise
     */
    public boolean doesProfilePictureExist(){
        return !this.getProfilePicture().isEmpty() && this.getProfilePicture() != null && !this.getProfilePicture().getName().equals("");
    }

    /**
     * Checks if file is an image
     * @return True if it's not an image, false otherwise
     */
    public boolean isFileNotAnImage(){
        String[] split = this.getProfilePicture().getOriginalFilename().split("\\.");
        return !this.getProfilePicture().isEmpty() &&
                !List.of("apng", "bmp", "gif", "jpeg", "pjpeg", "png", "svg", "tiff", "webp").contains(split[split.length - 1]);

    }

    /**
     * Check if the profile picture is too large
     * @return True if it's too big, false otherwise
     */
    public boolean isFileTooBig(){
        return this.doesProfilePictureExist() && this.getProfilePicture().getSize() > FileSystemStorageService.MAX_SIZE;
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
