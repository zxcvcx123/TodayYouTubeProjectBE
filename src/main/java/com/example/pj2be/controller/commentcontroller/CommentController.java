package com.example.pj2be.controller.commentcontroller;

import com.example.pj2be.domain.comment.CommentDTO;
import com.example.pj2be.service.commentservice.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService service;

    @PostMapping("/add")
    public void commentAdd(@RequestBody CommentDTO comment, String member_id) {
        System.out.println("member_id = " + member_id);
        service.commentAdd(comment, member_id);
    }

    @GetMapping("list")
    public List<CommentDTO> commentList(@RequestParam("board_id") Integer board_id) {
        return service.commentList(board_id);
    }

    @DeleteMapping("{comment_id}")
    public void commentRemove(@PathVariable Integer comment_id) {
        service.commentRemove(comment_id);
    }

    @PutMapping("edit")
    public void commentUpdate(@RequestBody CommentDTO comment) {
        service.commentUpdate(comment);
    }
}











