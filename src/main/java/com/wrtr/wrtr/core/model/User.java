package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio", length = 4096)
    private String bio;

    @OneToMany(targetEntity = Post.class, cascade = CascadeType.REMOVE, mappedBy = "author")
    private List<Post> postList;

    @OneToOne
    @JoinColumn(name = "profile_picture")
    private Resource profilePicture;

    @Column(name = "role", nullable = false)
    private String role;

    public User() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Resource getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Resource profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
