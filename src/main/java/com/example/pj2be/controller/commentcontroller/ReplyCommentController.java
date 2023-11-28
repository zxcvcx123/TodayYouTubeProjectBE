package com.example.pj2be.controller.commentcontroller;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import com.example.pj2be.service.commentservice.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/reply")
public class ReplyCommentController {

    private final ReplyCommentService service;


    @PostMapping("/add")
    public void add(@RequestBody ReplyCommentDTO replycomment) {
        service.add(replycomment);
    }

}
