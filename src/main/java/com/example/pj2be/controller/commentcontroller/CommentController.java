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
    public void add(@RequestBody CommentDTO comment) {
        service.add(comment);
    }

    @GetMapping("list")
    public List<CommentDTO> commentList(@RequestParam("id") Integer board_id) {
        return service.list(board_id);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        service.remove(id);
    }
}
