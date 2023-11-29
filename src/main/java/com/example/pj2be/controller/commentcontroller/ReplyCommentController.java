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
    public void reply_commentAdd(@RequestBody ReplyCommentDTO reply_comment) {
        service.reply_commentAdd(reply_comment);
    }

    @GetMapping("list")
    public List<ReplyCommentDTO> reply_commnetList(@RequestParam("reply_id") Integer comment_id) {
        return service.reply_commentList(comment_id);
    }

    @DeleteMapping("{reply_id}")
    public void reply_commentRemove(@PathVariable Integer reply_id) {
        service.reply_commentRemove(reply_id);
    }

    @PutMapping("edit")
    public void reply_commentUpdate(@RequestBody ReplyCommentDTO reply_comment) {
        service.reply_commentUpdate(reply_comment);
    }
}










