package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "content", length = 8192, nullable = false)
    private String content;


    @ManyToMany
    @JoinTable(
            name = "attachments",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "resource_id", nullable = false)
    )
    private Set<Resource> resourceSet;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    public Post() {}

    public Post(String content, User author){
        this.author = author;
        this.content = content;
        this.resourceSet = new HashSet<>();
        this.date = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Resource> getResourceSet() {
        return resourceSet;
    }

    public void setResourceSet(Set<Resource> resourceSet) {
        this.resourceSet = resourceSet;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
