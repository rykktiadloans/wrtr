package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.PlaceCanvas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the place canvas
 */
@Repository
public interface PlaceCanvasRepository extends JpaRepository<PlaceCanvas, Integer> {

    /**
     * Get the place canvas
     * @return Place canvas
     */
    @Query("FROM PlaceCanvas WHERE id = 1")
    public PlaceCanvas getCanvas();
}
