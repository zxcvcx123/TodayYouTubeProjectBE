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

    public Map<String, Object> update(CommentLikeDTO commentLike) {
        int count = 0;
        if (mapper.delete(commentLike) == 0) {
            count = mapper.insert(commentLike);
        }

        int countCommentLike = mapper.countByCommentId(commentLike.getComment_id());
        System.out.println("countCommentLike = " + countCommentLike);
        System.out.println("commentLike = " + commentLike);
        return Map.of("commentLike", count == 1,
                "countCommentLike", countCommentLike);
    }


}
