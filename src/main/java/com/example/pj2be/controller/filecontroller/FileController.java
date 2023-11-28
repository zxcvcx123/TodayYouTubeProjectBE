package com.example.pj2be.controller.filecontroller;

import com.example.pj2be.domain.file.FileDTO;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    // 게시판 번호에 따른 파일 가져오기
    @GetMapping("list/{id}")
    public ResponseEntity getFile(@PathVariable Integer id){

        System.out.println("@@@@@@ 파일 가져오기: " + id);
        List<FileDTO> fileList = fileService.getFile(id);

        return ResponseEntity.ok(fileList);
    }
}
