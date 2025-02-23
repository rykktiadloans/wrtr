package com.wrtr.wrtr.core.config;

import com.wrtr.wrtr.core.service.PlaceCanvasService;
import com.wrtr.wrtr.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * This class' method ensurePlaceCanvasExists is run on startup to make sure that a basic place canvas already exists
 */
@Component
public class DataInitializer {
    @Autowired
    private PlaceCanvasService placeCanvasService;

    @Autowired
    private UserService userService;

    /**
     * Is run on application startup to make sure that a place canvas service exists in the database
     */
    @EventListener(ApplicationReadyEvent.class)
    public void ensurePlaceCanvasExists() {
        this.placeCanvasService.ensurePlaceCanvasExists();
    }

    /**
     * Is run on application startup to make sure that a default admin user exists in the database
     */
    @EventListener(ApplicationReadyEvent.class)
    public void ensureAdminUserExists() {
        this.userService.ensureAdminUserExists();
    }
}
