package com.example.pj2be.controller.likecontroller;

import com.example.pj2be.service.likeservice.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like/comment")
public class CommentLikeController {

    private final CommentLikeService service;

}
