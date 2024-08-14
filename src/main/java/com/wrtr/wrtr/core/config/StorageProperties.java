package com.wrtr.wrtr.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration of the dynamic storage.
 */
@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {
    private String location = "upload-dir";

    /**
     * Get the location of the directory we save attachments at
     * @return Directory
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of the storage.
     * @param location The new directory
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
