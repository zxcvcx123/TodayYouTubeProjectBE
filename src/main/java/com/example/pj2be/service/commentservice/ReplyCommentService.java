package com.example.pj2be.service.commentservice;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import com.example.pj2be.mapper.commentmapper.ReplyCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyCommentService {

    private final ReplyCommentMapper mapper;

    public void reply_commentAdd(ReplyCommentDTO reply_comment, String member_id) {
        System.out.println("@@@@@@" + reply_comment.getId() + " 댓글 작성 @@@@@@");
//        reply_comment.setMember_id("testadmin");
        System.out.println("reply_comment = " + reply_comment);
        System.out.println("member_id = " + member_id);
        mapper.reply_commentInsert(reply_comment, member_id);
    }

    public List<ReplyCommentDTO> reply_commentList(Integer comment_id) {
        return mapper.reply_commentSelectByComment_id(comment_id);
    }

    public void reply_commentRemove(Integer reply_id) {
        System.out.println("@@@@@@" + reply_id + "댓글 삭제 @@@@@@");
        mapper.deleteByReply_id(reply_id);
    }

    public boolean reply_commentUpdate(ReplyCommentDTO reply_comment) {
        System.out.println("@@@@@@" + reply_comment.getId() + " 댓글 업뎃 @@@@@@");
        return mapper.reply_commentUpdate(reply_comment) == 1;
    }
}
