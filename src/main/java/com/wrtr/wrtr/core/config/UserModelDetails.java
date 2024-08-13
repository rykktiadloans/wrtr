package com.wrtr.wrtr.core.config;

import com.wrtr.wrtr.core.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class UserModelDetails implements UserDetails {
    private User user;

    public UserModelDetails(User user) {
        this.user = user;
    }

    /**
     * Get user's ID
     * @return User's ID
     */
    public UUID getId(){
        return this.user.getId();
    }

    /**
     * Returns user's authorities
     * @return User's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    /**
     * Return user's encrypted password
     * @return Encrypted password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Return user's username
     * @return User's username
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Return a value of whether or not the user's account has expired
     * @return Returns true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns the value that corresponds to whether or not the account is locked
     * @return Rturns true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Returns the value that corresponds to whether or not the account's credentials have expired
     * @return Returns true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Is the account enabled?
     * @return Returns true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
