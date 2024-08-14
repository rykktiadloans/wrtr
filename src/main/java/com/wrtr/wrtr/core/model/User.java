package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

/**
 * Persistent model class that contains info about a user
 */
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

    /**
     * Empty constructor
     */
    public User() {}

    /**
     * Get user's ID
     * @return User's UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set user's new ID
     * @param id New UUID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get user's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set a new username for the user
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get user's email
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set a new email for the user
     * @param email New email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user's password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a new password
     * @param password New password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user's bio
     * @return User's bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set user's new bio
     * @param bio New bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Get user's posts
     * @return User's posts
     */
    public List<Post> getPostList() {
        return postList;
    }

    /**
     * Set a new list of posts
     * @param postList New posts
     */
    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    /**
     * Get user's profile picture
     * @return Profile picture resource
     */
    public Resource getProfilePicture() {
        return profilePicture;
    }

    /**
     * Set a new profile picture
     * @param profilePicture New profile picture
     */
    public void setProfilePicture(Resource profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Get user's role
     * @return User's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Set user's new role
     * @param role New role
     */
    public void setRole(String role) {
        this.role = role;
    }
}
