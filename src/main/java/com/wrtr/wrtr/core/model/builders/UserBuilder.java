package com.wrtr.wrtr.core.model.builders;

import com.wrtr.wrtr.core.config.SecurityConfig;
import com.wrtr.wrtr.core.model.User;

import java.util.ArrayList;

/**
 * A builder class for User
 */
public class UserBuilder {
    private SecurityConfig securityConfig;
    private String username;
    private String email;
    private String password;
    private String role;

    /**
     * A default initializer. Requires the security config to get the password encoder
     * @param securityConfig Security configuration bean
     */
    public UserBuilder(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * Builds the final user class based on previously used methods
     * @return Resolved user
     */
    public User build() {
        var user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setRole(this.role);
        user.setPassword(this.securityConfig.passwordEncoder().encode(this.password));
        user.setPostList(new ArrayList<>());
        user.setBio("");
        user.setProfilePicture(null);
        return user;
    }

    /**
     * Adds the new username to the user
     * @param username User's username
     * @return The user builder (for chaining)
     */
    public UserBuilder addUsername(String username) {
        this.username = username;
        return this;
    }
    /**
     * Adds the email to the user
     * @param email User's email
     * @return The user builder (for chaining)
     */
    public UserBuilder addEmail(String email) {
        this.email = email;
        return this;
    }
    /**
     * Adds the password to the user
     * @param password User's password
     * @return The user builder (for chaining)
     */
    public UserBuilder addPassword(String password) {
        this.password = password;
        return this;
    }
    /**
     * Adds the role to the user
     * @param role User's role
     * @return The user builder (for chaining)
     */
    public UserBuilder addRole(String role) {
        this.role = role;
        return this;
    }
}
