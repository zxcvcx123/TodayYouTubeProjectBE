package com.example.pj2be.service.likeservice;

import com.example.pj2be.domain.like.BoardLikeDTO;
import com.example.pj2be.mapper.likemapper.BoardLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardLikeService {

    private final BoardLikeMapper mapper;


    public Map<String, Object> getFirstBoardLike(BoardLikeDTO boardLikeDTO) {


        // 게시글 좋아요 갯수
        int countlike = mapper.countLikeByBoardId(boardLikeDTO);

        int like = mapper.selectById(boardLikeDTO);

        return Map.of( "countlike", countlike, "like", like);
    }


    public Map<String, Object> getBoardLike(BoardLikeDTO boardLikeDTO) {


        // 게시글 좋아요 갯수
        int countlike = mapper.countLikeByBoardId(boardLikeDTO);


        return Map.of( "countlike", countlike);
    }



    public Map<String, Object> boardLike(BoardLikeDTO boardLikeDTO) {

        int count = 0;

        if (mapper.deleteById(boardLikeDTO) == 0){
            mapper.insertById(boardLikeDTO);
            count = 1;
        }

        return Map.of("like", count);
    }
}
