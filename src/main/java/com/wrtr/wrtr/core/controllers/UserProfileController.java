package com.wrtr.wrtr.core.controllers;

import com.wrtr.wrtr.core.exceptions.PostNotFoundException;
import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.PostDto;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserService;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controller used to access user's profiles
 */
@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private FileSystemStorageService storageService;

    /**
     * Redirects to the user's profile if the user is logged in, other wise redirects to the login page
     * @param authentication The authentication object we use to check credentials
     * @return Redirects to the user's profile page
     */
    @GetMapping(path = "/myprofile")
    public String getMyProfile(Authentication authentication){
        String id;
        try {
            id = this.userService.getUserByAuth(authentication).getId().toString();
        }
        catch (UsernameNotFoundException | NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return "redirect:/user/".concat(id);
    }

    /**
     * Builds and return's the selected user's profile page
     * @param userId User's ID passed in the URL
     * @param model The model object we use to populate the template
     * @param authentication The authentication object we use to check whether the logged in user should have control of the profile
     * @return The profile page of the user
     */
    @GetMapping(path = "/user/{userId}")
    public String getUserProfile(@PathVariable(value = "userId") String userId, Model model, Authentication authentication) {
        User user;
        try{
            user = this.userService.getUserById(UUID.fromString(userId));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        boolean canEdit = this.userService.isUsersPage(authentication, user);
        model.addAttribute("user", user);
        model.addAttribute("canEdit", canEdit);
        List<Post> posts = this.postService.getPostsMadeByUser(user);
        posts.sort(Comparator.comparing(o -> o.getDate().atZone(ZoneId.systemDefault()).toEpochSecond()));
        Collections.reverse(posts);
        List<List<String>> images = new ArrayList<>();
        List<List<String>> attachments = new ArrayList<>();
        List<List<String>> attachmentNames = new ArrayList<>();
        for(var post: posts){
            images.add(new ArrayList<>());
            attachments.add(new ArrayList<>());
            attachmentNames.add(new ArrayList<>());
            for(var res : post.getResourceSet()){
                String extension = "";
                String originalFilename = res.getPath();
                int lastDot = originalFilename.lastIndexOf(".");
                if(lastDot != -1){
                    extension = originalFilename.substring(lastDot);
                }
                if(List.of(".jpg", ".jpeg", ".png", ".avif", ".gif", ".svg", ".webp", ".bmp").contains(extension)){
                    images.get(images.size() - 1).add("/" + res.getPath());
                }
                else {
                    attachments.get(images.size() - 1).add("/" + res.getPath());
                    int lastSlash = res.getPath().lastIndexOf("/");
                    attachmentNames.get(images.size() - 1).add(res.getName());
                }
            }
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postDto", new PostDto(user.getId().toString(), new ArrayList<>()));
        model.addAttribute("images", images);
        model.addAttribute("attachments", attachments);
        model.addAttribute("attachmentNames", attachmentNames);
        DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm");

        return "profile";
    }

    /**
     * Method that handles creating and saving a new post
     * @param authentication Authentication object we use to check the credentials
     * @param model Model object
     * @param postDto The DTO object we use to assemble the post and it's attachments
     * @return Redirects to the profile page
     */
    @PostMapping(path = "/newpost")
    public String postNewPost(Authentication authentication, Model model, @ModelAttribute("postDto") PostDto postDto){
        User user;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (UsernameNotFoundException | NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(postDto.isContentTooLarge()){
            return "redirect:/myprofile";
        }
        if(postDto.isAnyFileTooBig()){
            return "redirect:/myprofile";
        }
        Post post = new Post(postDto.content(), user);
        Set<Resource> resources = new HashSet<>();
        for(var file: postDto.files()){
            if(file.getName().equals("")){
                continue;
            }
            Resource res = this.storageService.save(file);
            res.setPost(post);
            resources.add(res);
        }
        post.setResourceSet(resources);
        post.setDate(LocalDateTime.now());
        user.getPostList().add(post);
        this.userService.save(user);
        this.postService.save(post);

        return "redirect:/myprofile";
    }

    /**
     * Deletes the post with the supplied id
     * @param id Id of the post to delete
     * @param authentication Authentication object we use to authenticate the user
     * @return Redirects to /login if the user isn't logged or doesn't own the post, otherwise redirects to /myprofile
     */
    @DeleteMapping("/deletepost/{id}")
    public String deletePost(@PathVariable("id") String id, Authentication authentication){
        User user;
        Post post;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (NullPointerException | UsernameNotFoundException e){
            return "redirect:/login";
        }
        try {
            post = this.postService.getPostById(UUID.fromString(id));
        } catch (PostNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(post.getAuthor() != user){
            return "redirect:/login";
        }
        this.postService.deletePost(post);
        return "redirect:/myprofile";
    }

    /**
     * Delete attachments of the post with the matching id
     * @param id Id of the post whose attachments we want to delete
     * @param authentication Authentication object we use to identify the user
     * @return Redirect to login if the user isn't logged in or doesn't own the post, otherwise redirects to /myprofile
     */
    @DeleteMapping("/deleteattachments/{id}")
    public String deleteAttachments(@PathVariable("id") String id, Authentication authentication){
        User user;
        Post post;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (NullPointerException | UsernameNotFoundException e){
            return "redirect:/login";
        }
        try {
            post = this.postService.getPostById(UUID.fromString(id));
        } catch (PostNotFoundException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(post.getAuthor() != user){
            return "redirect:/login";
        }
        this.postService.deletePostsAttachments(post);
        return "redirect:/myprofile";
    }

    /**
     * An endpoint the lists all the users with the similar usernames
     * @param matchBy String to match by. Users with this string as a substring of their username are found
     * @param model Model object we use to populate the template
     * @return The searchusers template
     */
    @GetMapping("/search/users")
    public String getUsersWithMatchingUsername(@RequestParam("username") String matchBy, Model model){
        List<User> users = this.userService.searchUsersWithSimilarUsername(matchBy).stream().map((user) -> {
            if(user.getBio().length() < 200){
                return user;
            }
            user.setBio(user.getBio().substring(0, 200).concat("..."));
            return user;
        }).toList();
        model.addAttribute("users", users);
        return "searchusers";
    }

}
