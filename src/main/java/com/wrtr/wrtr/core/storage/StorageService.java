package com.wrtr.wrtr.core.storage;

import com.wrtr.wrtr.core.model.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * An interface for the dynamic storage service
 */
public interface StorageService {
    /**
     * Save the file and turn it into a resource
     * @param file File to save
     * @return Resource
     */
    Resource save(MultipartFile file);

    /**
     * Get the resource's path
     * @param resource Resource to use
     * @return The path
     */
    Path load(Resource resource);
}
