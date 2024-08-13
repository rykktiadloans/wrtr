package com.wrtr.wrtr.core.controllers;

import com.wrtr.wrtr.core.config.SecurityConfig;
import com.wrtr.wrtr.core.model.Resource;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.model.dto.UserDto;
import com.wrtr.wrtr.core.repository.ResourceRepository;
import com.wrtr.wrtr.core.repository.UserRepository;
import com.wrtr.wrtr.core.service.UserModelDetailsService;
import com.wrtr.wrtr.core.storage.StorageService;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class LoginController {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private StorageService storageService;

    /**
     * Return the login page on "/login"
     * @return The login page
     */
    @GetMapping("/login")
    String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    /**
     * Return the register page on "/register"
     * @return The register page
     */
    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Return the logout confirmation page on "/logout"
     * @return The logout page
     */
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    /**
     * Create a new account and redirect to "/login". Accessible on "/register" with POST method.
     * @return The login page
     */
    @PostMapping("/register")
    public String registerPost(Model model, @ModelAttribute("user") User user){
        user.setPassword(this.securityConfig.passwordEncoder().encode(user.getPassword()));
        user.setUsername(user.getEmail().replaceAll("@.*$", ""));
        user.setRole("user");
        user.setPostList(new ArrayList<>());
        user.setBio("");
        user.setProfilePicture(null);
        try{
            this.userRepository.save(user);
        }
        catch (RuntimeException e){
            return "redirect:/register?duplicate";
        }

        return "/login";

    }

    @GetMapping("/editprofile")
    public String getEditProfilePage(Model model, Authentication authentication){
        User user;
        try{
            user = this.userRepository.getUserByUsername(authentication.getName());
        }
        catch (UsernameNotFoundException | NullPointerException e){
            return "redirect:/login";
        }
        UserDto userDto = new UserDto(user.getUsername(), user.getBio(), null);
        model.addAttribute("userDto", userDto);
        return "editprofile";
    }

    @PutMapping("/editprofile")
    public String putEditProfile(@ModelAttribute("userDto") UserDto userDto, Authentication authentication){
        User user;
        try{
            user = this.userRepository.getUserByUsername(authentication.getName());
        }
        catch (UsernameNotFoundException | NullPointerException e){
            return "redirect:/login";
        }
        Resource resource = null;
        if(userDto.getProfilePicture() != null && !userDto.getProfilePicture().getName().equals("")){
            resource = this.storageService.save(userDto.getProfilePicture());
            this.resourceRepository.save(resource);
            user.setProfilePicture(resource);
        }
        user.setUsername(userDto.getUsername());
        user.setBio(userDto.getBio());
        this.userRepository.save(user);
        return "redirect:/myprofile";

    }
}
