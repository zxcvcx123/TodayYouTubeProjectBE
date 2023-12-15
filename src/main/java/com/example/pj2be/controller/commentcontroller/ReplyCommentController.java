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
    public ResponseEntity reply_commentAdd(@RequestBody ReplyCommentDTO replyCommentDTO) {
        if (!IsLoginMember(replyCommentDTO.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.reply_commentAdd(replyCommentDTO);

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
    public ResponseEntity reply_commentUpdate(@RequestBody ReplyCommentDTO replyCommentDTO) {
        service.reply_commentUpdate(replyCommentDTO);
        return ResponseEntity.ok().build();
    }

    // ========================= 투표 게시판 대댓글 =========================

    @PostMapping("/vote/add")
    public ResponseEntity voteReply_commentAdd(@RequestBody ReplyCommentDTO replyCommentDTO) {

        System.out.println("대댓 테스트: " + replyCommentDTO);
        if (!IsLoginMember(replyCommentDTO.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.voteReply_commentAdd(replyCommentDTO);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/vote/list")
    public List<ReplyCommentDTO> voteReply_commnetList(ReplyCommentDTO replyCommentDTO) {
        return service.voteReply_commentList(replyCommentDTO);
    }



}










