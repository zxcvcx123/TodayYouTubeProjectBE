package com.example.pj2be.service.likeservice;

import com.example.pj2be.mapper.likemapper.CommentLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentLikeService {

    private final CommentLikeMapper mapper;
}
