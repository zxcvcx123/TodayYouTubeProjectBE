package com.example.pj2be.controller.commentcontroller;

import com.example.pj2be.domain.comment.CommentDTO;
import com.example.pj2be.service.commentservice.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.pj2be.utill.MemberAccess.IsLoginMember;
import static com.example.pj2be.utill.MemberAccess.MemberChecked;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService service;

    @PostMapping("/add")
    public ResponseEntity commentAdd(@RequestBody CommentDTO comment, String member_id) {


        if (!IsLoginMember(comment.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.commentAdd(comment, member_id);
        System.out.println("member_id = " + member_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("list")
    public List<CommentDTO> commentList(@RequestParam("board_id") Integer board_id,
                                        @RequestParam(value = "member_id", defaultValue = "") String member_id) {
        return service.commentList(board_id, member_id);
    }

    @DeleteMapping("{comment_id}")
    public ResponseEntity commentRemove(@PathVariable Integer comment_id, CommentDTO comment) {

        service.commentRemove(comment_id);
        return ResponseEntity.ok().build();


    }

    @PutMapping("edit")
    public ResponseEntity commentUpdate(@RequestBody CommentDTO comment) {


        service.commentUpdate(comment);
        return ResponseEntity.ok().build();

    }
}











