package com.panonit.rereview.services.impl;

import com.panonit.rereview.domain.dtos.PhotoDto;
import com.panonit.rereview.services.PhotoService;
import com.panonit.rereview.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final StorageService storage;

    @Override
    public PhotoDto uploadPhoto(MultipartFile file) {
        log.info("Uploading file {} to {}", file.getOriginalFilename(), file.getSize());

        String photoId = UUID.randomUUID().toString();
        String url = storage.store(file, photoId);

        return PhotoDto.builder()
                .url(url)
                .uploadDate(LocalDateTime.now())
                .build();
    }

    @Override
    public Optional<Resource> getPhotoAsResource(String photoId) {
        return storage.loadAsResource(photoId);
    }
}
