package org.bachtx.wibuspringboot.services.impl;

import com.cloudinary.Cloudinary;
import lombok.extern.log4j.Log4j2;
import org.bachtx.wibuspringboot.exceptions.UploadFileException;
import org.bachtx.wibuspringboot.services.UploadFileService;
import org.bachtx.wibuspringboot.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UploadFileServiceImpl implements UploadFileService {
    private final Cloudinary cloudinary;
    private final FileUtil fileUtil;

    public UploadFileServiceImpl(Cloudinary cloudinary, FileUtil fileUtil) {
        this.cloudinary = cloudinary;
        this.fileUtil = fileUtil;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        try {
            File file = fileUtil.convertMultipartToFile(multipartFile);

            @SuppressWarnings("unchecked")
            Map<String, Object> resultMap = cloudinary.uploader().upload(file, new HashMap<String, Object>());
            return resultMap.get("url").toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new UploadFileException(e.getMessage());
        }
    }
}
