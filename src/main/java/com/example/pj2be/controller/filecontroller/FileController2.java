package com.example.pj2be.controller.filecontroller;

import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController2 {
    private final FileService fileService;

    @PostMapping("ckupload")
    public ResponseEntity ckUpload(@RequestParam(value = "file") MultipartFile file) throws Exception {

        System.out.println("file = " + file.getOriginalFilename());
        System.out.println("file.getSize() = " + file.getSize());
        System.out.println("file.getContentType() = " + file.getContentType());

        return ResponseEntity.ok(fileService.ckS3Upload(file));
    }
}
