package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO class for the posts
 * @param content Content of the post
 * @param files Post's attachments
 */
public record PostDto(String content, List<MultipartFile> files) {

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
        for(var file : this.files()){
            if(FileSystemStorageService.isFileTooLarge(file)){
                return true;
            }
        }
        return false;
    }

}
