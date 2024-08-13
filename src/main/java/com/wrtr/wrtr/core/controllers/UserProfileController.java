package com.wrtr.wrtr.core.controllers;

import com.wrtr.wrtr.core.config.UserModelDetails;
import com.wrtr.wrtr.core.model.Post;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.PostDto;
import com.wrtr.wrtr.core.repository.PostRepository;
import com.wrtr.wrtr.core.repository.UserRepository;
import com.wrtr.wrtr.core.service.PostService;
import com.wrtr.wrtr.core.service.UserModelDetailsService;
import com.wrtr.wrtr.core.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class UserProfileController {

    @Autowired
    private UserModelDetailsService userModelDetailsService;
    @Autowired
    private PostService postService;
    @Autowired
    private FileSystemStorageService storageService;

    @GetMapping(path = "/myprofile")
    public String getMyProfile(Authentication authentication){
        String id;
        try {
            id = this.userModelDetailsService.getUserByUsername(authentication.getName()).getId().toString();
        }
        catch (UsernameNotFoundException | NullPointerException e){
            return "redirect:/login";
        }
        return "redirect:/user/".concat(id);
    }

    @GetMapping(path = "/user/{userId}")
    public String getUserProfile(@PathVariable(value = "userId") String userId, Model model, Authentication authentication) {
        User user;
        try{
            user = this.userModelDetailsService.getUserById(UUID.fromString(userId));
        }
        catch (UsernameNotFoundException e){
            return "redirect:/";
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

    @PostMapping(path = "/newpost")
    public String postNewPost(Authentication authentication, Model model, @ModelAttribute("postDto") PostDto postDto){
        User user = this.userModelDetailsService.getUserById(UUID.fromString(postDto.getUserId()));
        if(!user.getId().toString().equals(postDto.getUserId())){
            return "redirect:/login";
        }
        Post post = new Post(postDto.getContent(), user);
        Set<Resource> resources = new HashSet<>();
        for(var file: postDto.getFiles()){
            if(file.getName().equals("")){
                continue;
            }
            Resource res = this.storageService.save(file);
            res.setPostSet(Set.of(post));
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
