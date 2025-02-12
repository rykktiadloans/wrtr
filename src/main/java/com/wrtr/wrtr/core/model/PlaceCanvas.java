package com.wrtr.wrtr.core.model;

import jakarta.persistence.*;

/**
 * A database entity that represents a place canvas
 */
@Entity
@Table(name = "place_canvas")
public class PlaceCanvas {
    /**
     * Maximum size of the canvas
     */
    public static final int MAX_SIZE = 8192;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "content", length = MAX_SIZE, nullable = true)
    private String content;

    /**
     * Empty constructor
     */
    public PlaceCanvas() {}

    /**
     * Full constructor
     * @param id ID of the place canvas
     * @param content Content of the canvas
     */
    public PlaceCanvas(int id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Get the ID of the place canvas
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set new content
     * @param content New content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get content of the place canvas
     * @return Content
     */
    public String getContent() {
        return content;
    }
}
