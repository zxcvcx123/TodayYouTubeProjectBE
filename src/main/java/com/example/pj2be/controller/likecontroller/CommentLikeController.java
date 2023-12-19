package com.example.pj2be.controller.likecontroller;

import com.example.pj2be.domain.like.CommentLikeDTO;
import com.example.pj2be.service.likeservice.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.pj2be.utill.MemberAccess.IsLoginMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/like")
public class CommentLikeController {

    private final CommentLikeService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> commentLike(@RequestBody CommentLikeDTO commentLike) {
        if (!IsLoginMember(commentLike.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(service.update(commentLike));
    }




}
