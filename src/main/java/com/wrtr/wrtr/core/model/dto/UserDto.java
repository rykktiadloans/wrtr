package com.wrtr.wrtr.core.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserDto {
    private String username;
    private String bio;
    private MultipartFile profilePicture;

    public UserDto() {}
    public UserDto(String username, String bio, MultipartFile profilePicture){
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }
}
