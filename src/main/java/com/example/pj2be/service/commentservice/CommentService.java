package com.example.pj2be.service.commentservice;


import com.example.pj2be.domain.comment.CommentDTO;
import com.example.pj2be.mapper.commentmapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper mapper;

    public void add(CommentDTO comment) {
        comment.setMember_id("testadmin");
        mapper.insert(comment);
    }
}
