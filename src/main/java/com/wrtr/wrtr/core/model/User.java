package com.wrtr.wrtr.core.model;

import com.wrtr.wrtr.core.model.dto.UserDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

/**
 * Persistent model class that contains info about a user
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * Maximum length of a username
     */
    public static final int USERNAME_SIZE = 255;
    /**
     * Maximum length of a password
     */
    public static final int PASSWORD_SIZE = 255;
    /**
     * Maximum length of the bio
     */
    public static final int BIO_SIZE = 4096;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false, length = USERNAME_SIZE)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = PASSWORD_SIZE)
    private String password;

    @Column(name = "bio", length = BIO_SIZE)
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
     * Check if any fields exceed its allowed size.
     * @return true if any exceed, false otherwise.
     */
    public boolean anySizeExceeds(){
       return this.getUsername().length() > USERNAME_SIZE || this.getPassword().length() > PASSWORD_SIZE || this.getBio().length() > BIO_SIZE;
    }

    /**
     * Checks whether the new password is too long or not
     * @param password The new password to check
     * @return True if it is too long, false otherwise
     */
    public boolean isNewPasswordTooLong(String password){
        return password.length() > PASSWORD_SIZE;
    }

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
