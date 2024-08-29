package com.wrtr.wrtr.core.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Controller that is used to get the resources
 */
@RestController
public class ResourceController {
    /**
     * Get controller that returns the resource. Accessible with GET "/upload-dir/{name}"
     *
     * @param name Name of the resource
     * @return The data
     */
    @GetMapping(path = "/upload-dir/{name}")
    public ResponseEntity<FileSystemResource> getResource(@PathVariable(name = "name") String name) {
        Path path = Path.of("upload-dir/" + name);
        if(!Files.exists(path)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                    .body(fileSystemResource);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
