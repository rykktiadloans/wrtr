package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToMany(mappedBy = "resourceSet")
    private Set<Post> postSet;

    @Column(name = "path", nullable = false)
    private String path;

    public Resource() {}

    public Resource(String path) {
        this.path = path;
        this.postSet = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
