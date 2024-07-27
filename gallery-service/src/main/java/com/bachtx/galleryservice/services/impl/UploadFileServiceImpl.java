package com.bachtx.galleryservice.services.impl;

import com.bachtx.galleryservice.dtos.response.UploadFileResponse;
import com.bachtx.galleryservice.exceptions.UploadFileException;
import com.bachtx.galleryservice.services.UploadFileService;
import com.bachtx.wibucommon.utils.FileUtil;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Log4j2
public class UploadFileServiceImpl implements UploadFileService {
    private final Cloudinary cloudinary;
    private final FileUtil fileUtil = new FileUtil();


    @Override
    public UploadFileResponse uploadFile(MultipartFile multipartFile) {
        log.info("Start upload {}", multipartFile.getOriginalFilename());
        try {
            File file = fileUtil.convertMultipartToFile(multipartFile);
            @SuppressWarnings("unchecked")
            Map<String, Object> resultMap = cloudinary.uploader().upload(file, new HashMap<String, Object>());
            log.info("End upload {}", multipartFile.getOriginalFilename());
            if(file.delete()){
                log.info("Delete local temp file {}", multipartFile.getOriginalFilename());
            }
            return UploadFileResponse.builder()
                    .url(resultMap.get("url").toString())
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new UploadFileException(e.getMessage());
        }
    }

    @Override
    public List<UploadFileResponse> uploadMultiFiles(MultipartFile[] files) {
        return Arrays.stream(files)
                .parallel()
                .map(this::uploadFile)
                .toList();
    }
}
