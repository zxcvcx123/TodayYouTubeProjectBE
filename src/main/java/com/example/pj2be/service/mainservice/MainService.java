package com.example.pj2be.service.mainservice;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.mapper.mainmapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class MainService {

    private final MainMapper mapper;


    public Map<String, Object> select(String c) {
        LocalDateTime b = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

//        rankingTime = (현재날짜 - ?일) 동안 최다 추천수 받은 날짜
        LocalDateTime rankingTime = b.minusDays(7);

        Map<String, Object> map = new HashMap<>();


//        category가 all일 경우에 다른 mapper메소드 실행
        if (c.equals("all")) {
            map.put("otherBoardList", mapper.selectOtherByALl(rankingTime));
            map.put("firstBoardList", mapper.selectFirstByAll(rankingTime));
        } else {
            map.put("otherBoardList", mapper.selectOtherByCategory(c, rankingTime));
            map.put("firstBoardList", mapper.selectFirstByCategory(c, rankingTime));
        }

        return map;
    }
}
