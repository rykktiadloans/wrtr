package com.wrtr.wrtr.core.controllers.api;

import com.wrtr.wrtr.core.model.PlaceCanvas;
import com.wrtr.wrtr.core.model.User;
import com.wrtr.wrtr.core.service.PlaceCanvasService;
import com.wrtr.wrtr.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * REST controller for accessing data related to the place canvas
 */
@RestController
@RequestMapping(path = "/api/place")
public class PlaceCanvasRestController {
    /**
     * The amount of seconds that needs to pass between user's interactions with the place canvas
     */
    public final long TIMEOUT_SECONDS = 60;
    @Autowired
    private PlaceCanvasService placeCanvasService;
    @Autowired
    private UserService userService;

    /**
     * Returns the string containing the place canvas
     * @return Place canvas string
     */
    @GetMapping(path = "/")
    public String getPlaceCanvas() {
        return this.placeCanvasService.getPlaceCanvas().getContent();
    }

    /**
     * Returns the amount of seconds that the user needs to wait before interacting with the place again
     * @param authentication User's authentication object
     * @return Timeout left in seconds
     */
    @GetMapping(path = "/timeout")
    public long getTimeout(Authentication authentication) {
        User user;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (UsernameNotFoundException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(user.getPlaceLastInteracted() == null) {
            user.setPlaceLastInteracted(LocalDateTime.now().minusSeconds(this.TIMEOUT_SECONDS * 2));
            this.userService.save(user);
        }
        long seconds = ChronoUnit.SECONDS.between(user.getPlaceLastInteracted(), LocalDateTime.now());
        if(seconds > this.TIMEOUT_SECONDS) {
            return 0;
        }
        return 60 - seconds;
    }

    /**
     * To interact with the place, a user can add or delete one character once every TIMEOUT_SECONDS seconds.
     * @param position Position where the key was pressed
     * @param key Key that was pressed
     * @param authentication User's authentication object
     * @return A place canvas or nothing if something went wrong
     */
    @PatchMapping(path = "/touch")
    public boolean putTouch(@RequestParam("position") int position, @RequestParam("key") String key, Authentication authentication) {
        User user;
        try {
            user = this.userService.getUserByAuth(authentication);
        }
        catch (UsernameNotFoundException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(user.getPlaceLastInteracted() != null
                && ChronoUnit.SECONDS.between(user.getPlaceLastInteracted(), LocalDateTime.now()) < this.TIMEOUT_SECONDS) {
            return false;
        }
        PlaceCanvas placeCanvas = this.placeCanvasService.getPlaceCanvas();
        String content = placeCanvas.getContent();
        if (key.equals("Backspace") && position <= content.length() && position > 0) {
            String begin = content.substring(0, position - 1);
            String end = content.substring(position);
            placeCanvas.setContent(begin + end);
            this.placeCanvasService.save(placeCanvas);
        }
        else if (key.equals("Delete") && position < content.length() && position >= 0) {
            String begin = content.substring(0, position);
            String end = content.substring(position + 1);
            placeCanvas.setContent(begin + end);
            this.placeCanvasService.save(placeCanvas);
        }
        else if (key.length() == 1 && position <= content.length() && position >= 0 && content.length() <= PlaceCanvas.MAX_SIZE) {
            String begin = content.substring(0, position);
            String end = content.substring(position);
            placeCanvas.setContent(begin + key + end);
            this.placeCanvasService.save(placeCanvas);
        }
        else {
            return false;
        }
        user.setPlaceLastInteracted(LocalDateTime.now());
        this.userService.save(user);
        return true;

    }
}
