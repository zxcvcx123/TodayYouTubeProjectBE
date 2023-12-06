package com.example.pj2be.controller.likecontroller;

import com.example.pj2be.domain.like.CommentLikeDTO;
import com.example.pj2be.service.likeservice.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/like")
public class CommentLikeController {

    private final CommentLikeService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> commentLike(@RequestBody CommentLikeDTO commentLike) {
        return ResponseEntity.ok(service.update(commentLike));
    }




}
