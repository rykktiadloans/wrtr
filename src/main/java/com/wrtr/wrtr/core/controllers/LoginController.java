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

/**
 * Controls creation and access of users
 */
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
     * @param model Model object we use to populate the template
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
     * @param model Model object we use to populate the template
     * @param user User object we work with
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

    /**
     * Gets the page the user can use to change stuff about their profile. Accessed with GET "/editprofile"
     * @param model Model object we use to populate the template
     * @param authentication The authentication object we use to determine the logged in user
     * @return The editprofile template if the user matches, redirects to the login page otherwise
     */
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

    /**
     * Used to actually change the profile of the user. Accessible with PUT "/editprofile"
     * @param userDto The DTO object we got from the "/editprofile" page with the new data
     * @param authentication The authentication object we use to get the current logged in user
     * @return Redirects to the user's profile if the credentials match. Otherwise redirects to the login page
     */
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
