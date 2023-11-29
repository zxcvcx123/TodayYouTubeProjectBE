package com.example.pj2be.controller.commentcontroller;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import com.example.pj2be.service.commentservice.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/reply")
public class ReplyCommentController {

    private final ReplyCommentService service;


    @PostMapping("/add")
    public void reply_commentAdd(@RequestBody ReplyCommentDTO replycomment) {
        service.add(replycomment);
    }

    @GetMapping("list")
    public List<ReplyCommentDTO> reply_commnetList(@RequestParam("reply_id") Integer comment_id) {
        return service.list(comment_id);
    }
}
