package com.wrtr.wrtr.core.storage;

import com.wrtr.wrtr.core.model.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    Resource save(MultipartFile file);
    Path load(Resource resource);
}
