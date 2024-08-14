package com.wrtr.wrtr.core.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Controller that is used to get the resources
 */
@RestController
public class ResourceController {
    /**
     * Get controller that returns the resource. Accessible with GET "/upload-dir/{name}"
     * @param name Name of the resource
     * @return The data
     */
    @GetMapping(path = "/upload-dir/{name}")
    public byte[] getResource(@PathVariable(name = "name") String name) {
        Path path = Path.of("upload-dir/" + name);
        if(!Files.exists(path)){
            return null;
        }
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(path);
        }
        catch (IOException e){
            return null;
        }
        return bytes;

    }
}
