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

    public void reply_commentAdd(ReplyCommentDTO replyCommentDTO) {
        System.out.println("@@@@@@ 대댓글 추가한 member_id = " + replyCommentDTO.getMember_id() + " 댓글번호 :" + replyCommentDTO.getComment_id()+ "  @@@@@@") ;

        mapper.reply_commentInsert(replyCommentDTO);
    }

    public List<ReplyCommentDTO> reply_commentList(Integer comment_id) {
        return mapper.reply_commentSelectByComment_id(comment_id);
    }

    public void reply_commentRemove(Integer reply_id) {
        System.out.println("@@@@@@" + reply_id + "댓글 삭제 @@@@@@");
        mapper.deleteByReply_id(reply_id);
    }

    public boolean reply_commentUpdate(ReplyCommentDTO replyCommentDTO) {
        System.out.println("@@@@@@" + replyCommentDTO.getId() + " 댓글 업뎃 @@@@@@");
        return mapper.reply_commentUpdate(replyCommentDTO) == 1;
    }

    //    ================== 투표 게시판 대댓글 ===================
    public void voteReply_commentAdd(ReplyCommentDTO replyCommentDTO) {
        System.out.println("@@@@@@ 대댓글 추가한 member_id = " + replyCommentDTO.getMember_id() + " 댓글번호 :" + replyCommentDTO.getComment_id()+ "  @@@@@@");



        mapper.reply_voteCommentInsert(replyCommentDTO);
    }

    public List<ReplyCommentDTO> voteReply_commentList(ReplyCommentDTO replyCommentDTO) {

        return mapper.voteReply_commentSelectByComment_id(replyCommentDTO);
    }
}
