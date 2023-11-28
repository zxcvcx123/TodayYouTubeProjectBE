package com.example.pj2be.service.likeservice;

import com.example.pj2be.domain.like.CommentLikeDTO;
import com.example.pj2be.mapper.likemapper.CommentLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentLikeService {

    private final CommentLikeMapper mapper;

    public Map<String, Object> getLike(Integer id) {
        CommentLikeDTO dto = new CommentLikeDTO();
        dto.setMember_id("testadmin");

        mapper.selectByBoardId(id, dto.getMember_id());

        return null;
    }
}
