package com.example.pj2be.controller.likecontroller;

import com.example.pj2be.domain.like.BoardLikeDTO;
import com.example.pj2be.service.likeservice.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like/board")
public class BoardLikeController {

    private final BoardLikeService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> getBoardLike(@RequestBody BoardLikeDTO boardLikeDTO) {
        System.out.println("처음 게시물 들어갈때 게시판 번호: " + boardLikeDTO.getBoard_id());
        return ResponseEntity.ok(service.getBoardLike(boardLikeDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> boardLike(@RequestBody BoardLikeDTO boardLikeDTO) {

        return ResponseEntity.ok(service.boardLike(boardLikeDTO));
    }

}
