package com.bikkadIt.service.impl;

import com.bikkadIt.exception.BadRequest;
import com.bikkadIt.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileNameWithExtension = filename + extension;
        String fullpathwithFileName = path + fileNameWithExtension;

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            File folder = new File(path);

            if (!folder.exists()) {

                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullpathwithFileName));
            return fileNameWithExtension;

        } else {

            throw new BadRequest("File with this extension not allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;


    }
}
