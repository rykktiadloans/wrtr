package com.wrtr.wrtr.core.model.dto;

import com.wrtr.wrtr.core.model.Resource;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A class that is used to effectively fetch the posts
 */
public class PostApiDto {
    private UUID postId;
    private String authorUsername;
    private UUID authorId;
    private String content;
    private Set<Resource> resourceSet = new HashSet<>();
    private LocalDateTime date;

    /**
     * Empty constructor
     */
    public PostApiDto() { }

    /**
     * Proper constructor
     * @param postId ID of the post
     * @param authorUsername Author's username
     * @param authorId Author's ID
     * @param content Content of the post
     * @param date Date when the post was created at
     */
    public PostApiDto(UUID postId, String authorUsername, UUID authorId, String content, LocalDateTime date) {
        this.postId = postId;
        this.authorUsername = authorUsername;
        this.authorId = authorId;
        this.content = content;
        this.date = date;
    }

    /**
     * Get author's username
     * @return Author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Set new author's username
     * @param authorUsername Author's username
     */
    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    /**
     * Get Author's ID
     * @return Author's ID
     */
    public UUID getAuthorId() {
        return authorId;
    }

    /**
     * Set new author's ID
     * @param authorId new author's ID
     */
    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    /**
     * Get post's content
     * @return Post's content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set post's new content
     * @param content New content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the set of resources of the post
     * @return Resources of the post
     */
    public Set<Resource> getResourceSet() {
        return resourceSet;
    }

    /**
     * Set the new set of resources of the post
     * @param resourceSet Resources of the post
     */
    public void setResourceSet(Set<Resource> resourceSet) {
        this.resourceSet = resourceSet;
    }

    /**
     * Get the date when the post was created
     * @return Date of the post
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Get a new date of the psot
     * @param date Date of the post
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Get ID of the post
     * @return Post's ID
     */
    public UUID getPostId() {
        return postId;
    }

    /**
     * Set the post's new ID
     * @param postId New post ID
     */
    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}
