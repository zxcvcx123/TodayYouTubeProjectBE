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

    public void add(ReplyCommentDTO replycomment) {
        replycomment.setMember_id("testadmin");
        mapper.insert(replycomment);
    }

    public List<ReplyCommentDTO> list(Integer comment_id) {
        return mapper.selectByComment_id(comment_id);
    }
}
