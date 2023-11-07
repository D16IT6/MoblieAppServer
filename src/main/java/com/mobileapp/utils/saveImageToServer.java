package com.mobileapp.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
public class saveImageToServer {

    public String saveImage(MultipartFile image,String url) {
        if (!image.isEmpty()) {
            try {
                Path upLoadDir = Paths.get("upload/");
                InputStream inputStream=image.getInputStream();
                String nameFile= LocalDateTime.now().toString().toLowerCase().trim().replace(':','-')+image.getOriginalFilename();
                Files.copy(inputStream,upLoadDir.resolve(nameFile), StandardCopyOption.REPLACE_EXISTING);
                return url+"upload/"+nameFile;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}
