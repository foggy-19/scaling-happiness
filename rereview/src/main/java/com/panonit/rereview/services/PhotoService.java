package com.panonit.rereview.services;

import com.panonit.rereview.domain.dtos.PhotoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService {

    PhotoDto uploadPhoto(MultipartFile file);

    Optional<Resource> getPhotoAsResource(String photoId);
}
