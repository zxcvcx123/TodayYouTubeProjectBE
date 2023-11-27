package com.example.pj2be.controller.likecontroller;

import com.example.pj2be.service.likeservice.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like/comment")
public class CommentLikeController {

    private final CommentLikeService service;

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> getLike(@PathVariable Integer id) {

        return ResponseEntity.ok(service.getLike(id));


    }

}
