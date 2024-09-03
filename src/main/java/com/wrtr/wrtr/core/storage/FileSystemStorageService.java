package com.wrtr.wrtr.core.storage;

import com.wrtr.wrtr.core.config.StorageProperties;
import com.wrtr.wrtr.core.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Service class that manages saving and retrieving files from the dynamic storage
 */
@Service
public class FileSystemStorageService implements StorageService{
    private final Path rootLocation;
    /**
     * Maximum filesize in bytes
     */
    public static final int MAX_SIZE = 1049000;

    /**
     * Constructor
     * @param properties Properties of the dynamic storage
     */
    @Autowired
    public FileSystemStorageService(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());

    }

    /**
     * Saves a file and turns it into a resource object
     * @param file File to save
     * @return New resource
     */
    @Override
    public Resource save(MultipartFile file) {
        Resource resource = new Resource();
        try {
            String extension = "";
            String originalFilename = file.getOriginalFilename();
            int lastDot = originalFilename.lastIndexOf(".");
            if(lastDot != -1){
                extension = originalFilename.substring(lastDot);
            }

            Random random = new Random();
            String extra = "00000000".concat(String.valueOf(random.nextInt(0, 100000000)));
            extra = extra.substring(extra.length() - 8);
            Path inter = this.rootLocation.resolve(Paths.get(extra + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS").format(LocalDateTime.now())
                    .concat(extension))).normalize();
            Path destinationFile = inter.toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())){
                throw new StorageException("Cannot store file outside of current directory");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            resource.setPath(inter.toString());
            resource.setName(file.getOriginalFilename());

        }
        catch (IOException e){
            throw new StorageException("Failed to store file: ", e);
        }
        return resource;
    }

    /**
     * Gets resource's path
     * @param resource Resource to use
     * @return Path to the resource
     */
    @Override
    public Path load(Resource resource) {
        return this.rootLocation.resolve(Paths.get(resource.getPath()));
    }

    /**
     * Checks if the file is too large
     * @param file File which size to check
     * @return True if it's too large, false otherwise
     */
    public static boolean isFileTooLarge(MultipartFile file){
        return file.getSize() > MAX_SIZE;
    }
}
