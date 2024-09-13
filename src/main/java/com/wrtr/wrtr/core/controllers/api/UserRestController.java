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

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UserService userService;

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

    @GetMapping("/csrf")
    public CsrfToken getApiCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}
