package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A persistent model class that contains information about a post
 */
@Entity
@Table(name = "posts")
public class Post {

    public static final int CONTENT_SIZE = 8192;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "content", length = CONTENT_SIZE, nullable = false)
    private String content;


    @OneToMany(targetEntity = Resource.class, cascade = CascadeType.REMOVE, mappedBy = "post")
    private Set<Resource> resourceSet;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    /**
     * Empty constructor
     */
    public Post() {}

    /**
     * The complete constructor
     * @param content Post's content
     * @param author Author of the post
     */
    public Post(String content, User author){
        this.author = author;
        this.content = content;
        this.resourceSet = new HashSet<>();
        this.date = LocalDateTime.now();
    }

    /**
     * Get post's id
     * @return Post's UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set post's id
     * @param id Post's new UUID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Return the post's content
     * @return Post's content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the post's content
     * @param content New content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the set of attachments
     * @return Post's attachments
     */
    public Set<Resource> getResourceSet() {
        return resourceSet;
    }

    /**
     * Set a new set of attachments
     * @param resourceSet New attachments
     */
    public void setResourceSet(Set<Resource> resourceSet) {
        this.resourceSet = resourceSet;
    }

    /**
     * Get post's author
     * @return Post's author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets post's new author
     * @param author New author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Get the post's creation date
     * @return Creation date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Set the post's new creation date
     * @param date New date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
