package com.example.pj2be.service.commentservice;


import com.example.pj2be.domain.comment.CommentDTO;
import com.example.pj2be.mapper.commentmapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper mapper;

    public void add(CommentDTO comment) {
        comment.setMember_id("testadmin");
        mapper.insert(comment);
    }


    public List<CommentDTO> list(Integer board_id) {
        return mapper.selectByBoard_id(board_id);
    }

    public void remove(Integer id) {
        mapper.deleteById(id);
    }

    public boolean update(CommentDTO comment) {
        return mapper.update(comment) == 1;
    }
}
