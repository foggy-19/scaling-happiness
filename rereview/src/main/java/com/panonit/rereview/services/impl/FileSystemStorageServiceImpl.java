package com.panonit.rereview.services.impl;

import com.panonit.rereview.exceptions.StorageException;
import com.panonit.rereview.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageServiceImpl implements StorageService {

    private Path root;

    @Value("${app.storage.location:uploads}") // uploads is set as default
    private String storageLocation;

    @PostConstruct
    public void init() {
        log.info("Initializing storage service...");

        root = Paths.get(storageLocation);
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, String filename) {
        FileSystemStorageServiceImpl.log.info("Storing file {} ", filename);

        if (file.isEmpty()) {
            throw new StorageException("Cannot store an empty file");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String finalFilename = filename + "." + extension;
        Path destinationFile = root.resolve(Paths.get(finalFilename)).normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(root.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside specified directory");
        }

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Could not open input stream", e);
        }

        FileSystemStorageServiceImpl.log.info("Stored file {} to {}", filename, finalFilename);

        return finalFilename;
    }

    @Override
    public Optional<Resource> loadAsResource(String filename) {
        log.info("Loading file {} ", filename);

        Path file = root.resolve(filename);

        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return Optional.of(resource);
            }
        } catch (MalformedURLException e) {
            log.warn("Could not load file: {}.", filename, e);
        }

        return Optional.empty();
    }
}
