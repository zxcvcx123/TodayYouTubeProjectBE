package com.example.pj2be.service.likeservice;

import com.example.pj2be.domain.like.BoardLikeDTO;
import com.example.pj2be.mapper.likemapper.BoardLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardLikeService {

    private final BoardLikeMapper mapper;

    public Map<String, Object> getLike(Integer id) {
        BoardLikeDTO dto = new BoardLikeDTO();
        dto.setBoard_id(id);

        // test계정 셋팅
        dto.setMember_id("test");
        boolean likey = false;

        // 계정의 좋아요 여부
        if (mapper.selectByTestId(dto) == 1) {
          likey = true;
        } else {
            likey = false;
        }

        // 게시글 좋아요 갯수
        int countlike = mapper.countLikeByBoardId(dto);


        return Map.of( "like", likey, "countlike", countlike);
    }

    public Map<String, Object> like(Integer id) {
        BoardLikeDTO dto = new BoardLikeDTO();
        dto.setMember_id("test");

        int count = 0;
        if (mapper.deleteByTestId(dto.getMember_id(), id) == 0){
            mapper.insertByTestId(dto.getMember_id(), id);
            count = 1;
        }

        return Map.of("like", count);
    }
}
