package com.wrtr.wrtr.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
