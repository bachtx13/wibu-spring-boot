package com.bachtx.galleryservice.services;

import com.bachtx.galleryservice.dtos.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileService {
    UploadFileResponse uploadFile(MultipartFile file);
    List<UploadFileResponse> uploadMultiFiles(MultipartFile[] files);
}
