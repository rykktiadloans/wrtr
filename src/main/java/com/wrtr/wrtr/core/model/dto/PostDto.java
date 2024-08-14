package com.wrtr.wrtr.core.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO class for the post object and its attachments
 */
public class PostDto {
    private String userId;
    private String content;
    private List<MultipartFile> files = new ArrayList<>();

    /**
     * Empty constructor
     */
    public PostDto() {}

    /**
     * Proper constructor
     * @param userId Id of the user
     * @param content Content of the post
     */
    public PostDto(String userId, String content) {
        this.files = new ArrayList<>();
        this.userId = userId;
        this.content = content;
    }

    /**
     * Get the user's ID
     * @return User's ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set user's ID
     * @param userId User ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the content of the post
     * @return Post content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the post content
     * @param content New content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the list of files attached to the post
     * @return The list of file attachments
     */
    public List<MultipartFile> getFiles() {
        return files;
    }

    /**
     * Set the new list of file attachments
     * @param files New list of file attachments
     */
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
