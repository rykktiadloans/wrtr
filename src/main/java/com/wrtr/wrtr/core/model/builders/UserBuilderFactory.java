package com.wrtr.wrtr.core.model.builders;

import com.wrtr.wrtr.core.config.SecurityConfig;
import com.wrtr.wrtr.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * A class that can create user builder with the default encrypter
 * TODO: figure out a way to make it simpler
 */
@Component
public class UserBuilderFactory {
    @Autowired
    private SecurityConfig securityConfig;

    /**
     * Creates a UserBuilder with appropriate password encoder
     * @return Default UserBuilder
     */
    public UserBuilder createUserBuilder() {
        return new UserBuilder(this.securityConfig.passwordEncoder());
    }

    /**
     * A builder class for User
     */
    public static class UserBuilder {
        private final BCryptPasswordEncoder passwordEncoder;
        private String username;
        private String email;
        private String password;
        private String role;

        /**
         * A default initializer. Requires the used password encoder
         * @param encoder Password encoder
         */
        public UserBuilder(BCryptPasswordEncoder encoder) {
            this.passwordEncoder = encoder;
            this.username = "";
            this.email= "";
            this.password= "";
            this.role = "";
        }

        /**
         * Builds the final user class based on previously used methods
         * Note: this <b>doesn't</b> save the user to the database
         * @return Resolved user
         */
        public User build() {
            if(this.username.isEmpty() || this.email.isEmpty() || this.password.isEmpty() || this.role.isEmpty()) {
                throw new IllegalStateException("To create a user with a builder, all suppliable parts of a user have to be not empty.");
            }
            var user = new User();
            user.setUsername(this.username);
            user.setEmail(this.email);
            user.setRole(this.role);
            user.setPassword(this.passwordEncoder.encode(this.password));
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
}
