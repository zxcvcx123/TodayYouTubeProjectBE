package com.example.pj2be.controller.filecontroller;

import com.example.pj2be.domain.file.FileDTO;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    // 게시판 번호에 따른 파일 가져오기
    @GetMapping("list/{id}")
    public ResponseEntity getFile(@PathVariable Integer id){

        List<FileDTO> fileList = fileService.getFile(id);

        return ResponseEntity.ok(fileList);
    }

    // CK 에디터 이미지 업로드
    @PostMapping("ckupload")
    public ResponseEntity ckUpload(@RequestParam(value = "file") MultipartFile file,
                                   @RequestParam(value = "imgFile") Integer imgFile) throws Exception {


        if(file.getSize() >= 500) {

            if (imgFile <= 5) {
                return ResponseEntity.ok(fileService.ckS3Upload(file));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.badRequest().body("파일 크기가 너무 큽니다 1개당 최대(500kb)");
    }

    // 수정 화면에서 기존 파일 삭제
    @DeleteMapping("delete/{boardId}/{id}")
    public ResponseEntity deleteFileById(@PathVariable Integer boardId,
                                         @PathVariable Integer id){

        if(fileService.deleteFileById(id)){
            return ResponseEntity.ok(fileService.getFile(boardId));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
