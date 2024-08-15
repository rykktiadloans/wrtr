package com.wrtr.wrtr.core.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private UserService userModelDetailsService;
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
            id = this.userModelDetailsService.getUserByUsername(authentication.getName()).getId().toString();
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
            user = this.userModelDetailsService.getUserById(UUID.fromString(userId));
        }
        catch (IllegalArgumentException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String currectUser = authentication == null ? "" : authentication.getName();
        boolean canEdit = Objects.equals(currectUser, user.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("canEdit", canEdit);
        List<Post> posts = user.getPostList();
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
                    images.getLast().add("/" + res.getPath());
                }
                else {
                    attachments.getLast().add("/" + res.getPath());
                    int lastSlash = res.getPath().lastIndexOf("/");
                    attachmentNames.getLast().add(res.getPath().substring(lastSlash + 1));
                }
            }
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postDto", new PostDto(user.getId().toString(), ""));
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
            user = this.userModelDetailsService.getUserByUsername(authentication.getName());
        }
        catch (UsernameNotFoundException | NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(!user.getId().toString().equals(postDto.getUserId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Post post = new Post(postDto.getContent(), user);
        Set<Resource> resources = new HashSet<>();
        for(var file: postDto.getFiles()){
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
        this.userModelDetailsService.save(user);
        this.postService.save(post);

        return "redirect:/myprofile";
    }
}
