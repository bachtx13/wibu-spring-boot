package org.bachtx.wibuspringboot.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Component
public class FileUtil {
    public File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNullElse(multipartFile.getOriginalFilename(), ""));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
            return convFile;
        }
    }
}
