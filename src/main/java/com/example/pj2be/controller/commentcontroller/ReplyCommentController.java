package com.example.pj2be.controller.commentcontroller;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import com.example.pj2be.service.commentservice.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.pj2be.utill.MemberAccess.IsLoginMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/reply")
public class ReplyCommentController {

    private final ReplyCommentService service;

    @PostMapping("/add")
    public ResponseEntity reply_commentAdd(@RequestBody ReplyCommentDTO reply_comment, String member_id) {
        if (!IsLoginMember(reply_comment.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.reply_commentAdd(reply_comment, member_id);
        System.out.println("member_id = " + member_id);
        return ResponseEntity.ok().build();


    }

    @GetMapping("list")
    public List<ReplyCommentDTO> reply_commnetList(@RequestParam("reply_id") Integer comment_id) {
        return service.reply_commentList(comment_id);
    }

    @DeleteMapping("{reply_id}")
    public ResponseEntity reply_commentRemove(@PathVariable Integer reply_id) {

        service.reply_commentRemove(reply_id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("edit")
    public ResponseEntity reply_commentUpdate(@RequestBody ReplyCommentDTO reply_comment) {
        service.reply_commentUpdate(reply_comment);
        return ResponseEntity.ok().build();
    }
}










