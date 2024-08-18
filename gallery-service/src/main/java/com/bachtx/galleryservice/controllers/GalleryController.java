package com.bachtx.galleryservice.controllers;

import com.bachtx.galleryservice.dtos.response.UploadFileResponse;
import com.bachtx.galleryservice.services.UploadFileService;
import com.bachtx.wibucommon.dtos.response.ResponseTemplate;
import com.bachtx.wibucommon.enums.EResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GalleryController {
    private final UploadFileService uploadFileService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<UploadFileResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseTemplate.<UploadFileResponse>builder()
                .data(uploadFileService.uploadFile(file))
                .status(EResponseStatus.SUCCESS)
                .message("Upload photo successfully")
                .build();
    }
    @RequestMapping(value = "multi-upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<List<UploadFileResponse>> uploadMultiFiles(@RequestPart("files") MultipartFile[] files) {

        return ResponseTemplate.<List<UploadFileResponse>>builder()
                .data(uploadFileService.uploadMultiFiles(files))
                .status(EResponseStatus.SUCCESS)
                .message("Upload photos successfully")
                .build();
    }
}
