package com.wrtr.wrtr.core.service;

import com.wrtr.wrtr.core.model.PlaceCanvas;
import com.wrtr.wrtr.core.repository.PlaceCanvasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with the place canvas
 */
@Service
public class PlaceCanvasService {

    @Autowired
    private PlaceCanvasRepository placeCanvasRepository;

    /**
     * Returns the place canvas
     * @return Place canvas
     */
    public PlaceCanvas getPlaceCanvas() {
        return this.placeCanvasRepository.getCanvas();
    }

    /**
     * Creates a place canvas if one didn't exist before
     */
    public void ensurePlaceCanvasExists() {
        var placeCanvas = this.placeCanvasRepository.getCanvas();
        if(placeCanvas == null) {
            placeCanvas = new PlaceCanvas(1, "Sampley texte");
            this.placeCanvasRepository.save(placeCanvas);
        }
    }

    /**
     * Saves place canvas to the DB
     * @param placeCanvas Place canvas to save
     * @return Saved place canvas
     */
    public PlaceCanvas save(PlaceCanvas placeCanvas) {
        return this.placeCanvasRepository.save(placeCanvas);
    }

}
