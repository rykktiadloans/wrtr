package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO class for the post object and its attachments
 */
public class PostDto {

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
        this.content = content;
    }

    /**
     * Check if content of the post is too long
     * @return True if it's too large, false otherwise
     */
    public boolean isContentTooLarge(){
        return this.content.length() > Post.CONTENT_SIZE;
    }

    /**
     * Check if any files are too big
     * @return True if any are too big, false otherwise
     */
    public boolean isAnyFileTooBig(){
        for(var file : this.getFiles()){
            if(file.getSize() > FileSystemStorageService.MAX_SIZE){
                return true;
            }
        }
        return false;
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
