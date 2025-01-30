package com.wrtr.wrtr.core.controllers.api;

import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.PostApiDto;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
     * @param page Specifies a page to look up
     * @return List of posts made by the user
     */
    @GetMapping(path = "/")
    public List<PostApiDto> getPostsByUser(@RequestParam("userId") String userId, @RequestParam(name= "page", required = true) Integer page) {
        User user;
        try{
            user = this.userService.getUserById(UUID.fromString(userId));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<PostApiDto> posts;
        posts = this.postService.getPostApiDtosMadeByUser(user, PageRequest.of(page, PostRepository.PAGE_SIZE)).toList();
        return posts;
    }

    /**
     * Gets a page from the user's feed
     * @param authentication Authentication object
     * @param page Specifies a page to look up
     * @return List of PostApiDtos made by user's that the authenticated user follows
     */
    @GetMapping(path = "/feed")
    public List<PostApiDto> getFeed(Authentication authentication, @RequestParam(name = "page") Integer page) {
        User user;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (NullPointerException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return this.postService.getPostApiDtosMadeByFollowedAccounts(user, PageRequest.of(page, PostRepository.PAGE_SIZE)).toList();

    }


}
