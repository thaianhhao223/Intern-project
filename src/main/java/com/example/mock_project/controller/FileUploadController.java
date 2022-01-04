package com.example.mock_project.controller;

import com.example.mock_project.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.Writer;

/**
 * Dùng để test chức năng upload file
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public String postFile(@RequestPart(value = "file") MultipartFile file){
        String url = "";
        url = storageService.store(file);
        return url;
    }
    @PostMapping("/multifile")
    public String postMutiFile(@RequestPart(value = "files") MultipartFile[] file){
        System.out.println(file[1].getName());
        for(MultipartFile singleFile: file){
            storageService.store(singleFile);
        }
        return " You successfully uploaded!";
    }
}
