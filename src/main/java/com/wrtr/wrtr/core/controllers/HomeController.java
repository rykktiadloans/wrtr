package com.wrtr.wrtr.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller we use for accessing the home page
 */
@Controller
public class HomeController {
    /**
     * Returns the homepage
     * @return The home page template
     */
    @GetMapping(path = "/")
    public String home() {
        return "home";
    }
}
