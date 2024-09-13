package com.wrtr.wrtr.core.controllers.api;

import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * REST controller for the user model
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    /**
     * Fetches the data about the user
     * @param id Id of the user
     * @return The user, if found
     */
    @GetMapping(path = "/{id}")
    public User getApiUser(@PathVariable("id") String id) {
        User user;
        try{
            user = this.userService.getUserById(UUID.fromString(id));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        user.setPostList(null);
        return user;
    }

    /**
     * Fetch the information whether the logged in user matches the one in the request
     * @param id Id of the user
     * @param authentication authentication object of the logged in user
     * @return True if the users match, false otherwise
     */
    @GetMapping(path = "/canEdit")
    public boolean getApiCanEdit(@RequestParam("userId") String id, Authentication authentication) {
        User pageUser;
        User loggedInUser;
        try{
            pageUser = this.userService.getUserById(UUID.fromString(id));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try{
            loggedInUser = this.userService.getUserByAuth(authentication);
        }
        catch (NullPointerException | UsernameNotFoundException e){
            return false;
        }
        return pageUser.getEmail().equals(loggedInUser.getEmail());
    }

    /**
     * Checks whether the user is logged in at all
     * @param authentication Authentication object of the supposedly logged in user
     * @return True if the user is logged in, false otherwise
     */
    @GetMapping("/isLoggedIn")
    public boolean getApiIsLoggedIn(Authentication authentication) {
        User user;
        try{
            user = this.userService.getUserByAuth(authentication);
        }
        catch (NullPointerException | UsernameNotFoundException e){
            return false;
        }
        return true;

    }

    /**
     * Get the anti-CSRF token
     * @param csrfToken CSRF token object to return
     * @return Matching CSRF token
     */
    @GetMapping("/csrf")
    public CsrfToken getApiCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}
