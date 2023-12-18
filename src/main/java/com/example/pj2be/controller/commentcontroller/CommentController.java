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
    public ResponseEntity commentAdd(@RequestBody CommentDTO commentDTO, String member_id) {


        if (!IsLoginMember(commentDTO.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.commentAdd(commentDTO, member_id);
        System.out.println("member_id = " + member_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("list")
    public List<CommentDTO> commentList(@RequestParam("board_id") Integer board_id,
                                        @RequestParam(value = "member_id", defaultValue = "") String member_id,
                                        CommentDTO commentDTO) {
        return service.commentList(board_id, member_id, commentDTO);
    }

    @DeleteMapping("{comment_id}")
    public ResponseEntity commentRemove(@PathVariable Integer comment_id, CommentDTO comment) {

        service.commentRemove(comment_id);
        return ResponseEntity.ok().build();


    }

    @PutMapping("edit")
    public ResponseEntity commentUpdate(@RequestBody CommentDTO commentDTO) {


        service.commentUpdate(commentDTO);
        return ResponseEntity.ok().build();

    }

    // ==================== 투표 게시판 댓글 ======================

    @PostMapping("/vote/add")
    public ResponseEntity VoteCommentAdd(@RequestBody CommentDTO commentDTO, String member_id) {


        if (!IsLoginMember(commentDTO.getMember_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        service.voteCommentAdd(commentDTO, member_id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("vote/list")
    public List<CommentDTO> VoteCommentList(CommentDTO commentDTO) {
        return service.VoteCommentList(commentDTO);
    }
}











