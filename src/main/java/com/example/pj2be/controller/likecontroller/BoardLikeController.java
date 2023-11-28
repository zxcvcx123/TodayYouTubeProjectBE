package com.example.pj2be.controller.likecontroller;

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

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> getLike(@PathVariable Integer id) {

        return ResponseEntity.ok(service.getLike(id));
    }

    @PostMapping("{id}")
    public ResponseEntity<Map<String, Object>> like(@PathVariable Integer id) {

        return ResponseEntity.ok(service.like(id));
    }

}
