package org.bachtx.wibuspringboot.controllers.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.controllers.UploadFileController;
import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.UploadFileResponse;
import org.bachtx.wibuspringboot.enums.EResponseStatus;
import org.bachtx.wibuspringboot.services.UploadFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
@AllArgsConstructor
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class UploadFileControllerImpl implements UploadFileController {
    private final UploadFileService uploadFileService;

    @Override
    public BaseResponse<UploadFileResponse> uploadPhoto(MultipartFile file) {
        return BaseResponse.<UploadFileResponse>builder()
                .data(UploadFileResponse.builder()
                        .url(uploadFileService.uploadFile(file))
                        .build())
                .status(EResponseStatus.SUCCESS)
                .message("Upload photo successfully")
                .build();
    }
}
