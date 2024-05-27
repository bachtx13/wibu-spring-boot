package org.bachtx.wibuspringboot.controllers;

import org.bachtx.wibuspringboot.dtos.response.BaseResponse;
import org.bachtx.wibuspringboot.dtos.response.UploadFileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileController {
    @RequestMapping(value = "photo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    BaseResponse<UploadFileResponse> uploadPhoto(@RequestParam("file") MultipartFile file);
}
