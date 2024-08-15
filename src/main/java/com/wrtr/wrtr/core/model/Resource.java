package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Persistent model class that contains info about a resource (a file in the dynamic storage)
 */
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "path", nullable = false)
    private String path;

    /**
     * Empty constructor
     */
    public Resource() {}

    /**
     * Completeconstructor
     * @param path Path to the file
     */
    public Resource(String path) {
        this.path = path;
    }

    /**
     * Get resource's ID
     * @return Resource's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set a new ID
     * @param id New ID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get a the post that the resource is attached to
     * @return Parent post
     */
    public Post getPost() {
        return post;
    }

    /**
     * Set a new post the resource is attached to
     * @param post New post
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Get the path to the file
     * @return Path to the file
     */
    public String getPath() {
        return path;
    }

    /**
     * Set a new path to the file
     * @param path New path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
