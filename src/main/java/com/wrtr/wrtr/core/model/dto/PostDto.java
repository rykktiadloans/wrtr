package com.wrtr.wrtr.core.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private String userId;
    private String content;
    private List<MultipartFile> files = new ArrayList<>();

    public PostDto() {}
    public PostDto(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
