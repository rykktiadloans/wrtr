package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The DTO record for the User objects
 */
public record UserDto(String username, String bio, MultipartFile profilePicture) {


    /**
     * Checks if the username is too large
     * @return True if it's too large, false otherwise
     */
    public boolean isUsernameTooLarge(){
        return this.username().length() > User.USERNAME_SIZE;
    }

    /**
     * Checks if the bio is too large
     * @return True if it's too large, false otherwise
     */
    public boolean isBioTooLarge(){
        return this.bio.length() > User.BIO_SIZE;
    }

    /**
     * Checks if the profile picture was supplied
     * @return Returns true if picture was supplied, false otherwise
     */
    public boolean doesProfilePictureExist(){
        return this.profilePicture != null && !this.profilePicture.isEmpty() && !this.profilePicture.getName().equals("");
    }

    /**
     * Checks if file is an image
     * @return True if it's not an image, false otherwise
     */
    public boolean isFileNotAnImage(){
        String[] split = this.profilePicture.getOriginalFilename().split("\\.");
        return !this.profilePicture.isEmpty() &&
                !List.of("apng", "bmp", "gif", "jpeg", "pjpeg", "png", "svg", "tiff", "webp").contains(split[split.length - 1]);

    }

    /**
     * Check if the profile picture is too large
     * @return True if it's too big, false otherwise
     */
    public boolean isFileTooBig(){
        return this.doesProfilePictureExist() && FileSystemStorageService.isFileTooLarge(this.profilePicture);
    }
}
