package com.example.pj2be.service.commentservice;


import com.example.pj2be.domain.comment.CommentDTO;
import com.example.pj2be.mapper.commentmapper.CommentMapper;
import com.example.pj2be.mapper.commentmapper.ReplyCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper mapper;
    private final ReplyCommentMapper replyCommentMapper;

    public void commentAdd(CommentDTO comment, String member_id) {
        System.out.println("@@@@@@" + comment.getId() + " 댓글 작성 @@@@@@");
//        comment.setMember_id("testadmin");
        System.out.println("member_id = " + member_id);
        mapper.commentInsert(comment);
    }


    public List<CommentDTO> commentList(Integer board_id, String member_id) {
        return mapper.commentSelectByBoard_id(board_id, member_id);
    }


    public void commentRemove(Integer comment_id) {
        System.out.println("@@@@@@" + comment_id + "댓글 삭제 @@@@@@");

        // 대댓글 레코드 먼저 지우기
        replyCommentMapper.DeleteByCommentId(comment_id);

        mapper.commentDeleteById(comment_id);
    }

    public boolean commentUpdate(CommentDTO comment) {
        System.out.println("@@@@@@" + comment.getId() + " 댓글 업뎃 @@@@@@");
        return mapper.commentUpdate(comment) == 1;
    }
}
