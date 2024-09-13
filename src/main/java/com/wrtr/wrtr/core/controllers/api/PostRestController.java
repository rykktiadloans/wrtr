package com.wrtr.wrtr.core.controllers.api;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * REST controller class we use to access posts
 */
@RestController
@RequestMapping(path = "/api/posts")
public class PostRestController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    /**
     * Gets all the posts made by a user
     * @param userId Id of the user
     * @return List of posts made by the user
     */
    @GetMapping(path = "/")
    public List<Post> getPostsByUser(@RequestParam("userId") String userId) {
        User user;
        try{
            user = this.userService.getUserById(UUID.fromString(userId));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var posts = this.postService.getPostsMadeByUser(user);
        return posts;
    }
}
