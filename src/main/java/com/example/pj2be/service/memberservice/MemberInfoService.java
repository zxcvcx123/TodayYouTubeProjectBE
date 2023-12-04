package com.example.pj2be.service.memberservice;

import com.example.pj2be.mapper.membermapper.MemberInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoMapper memberInfoMapper;

    public Map<String, Object> getMyBoardList(String member_id, String categoryOrdedBy, String categoryTopics) {
        Map<String, Object> myBoardListMap = new HashMap<>();
        Map<String, Object> pagingInformation = new HashMap<>();

        myBoardListMap.put("myBoardList", memberInfoMapper.getMyBoardList(member_id, categoryOrdedBy, categoryTopics));

        return myBoardListMap;
    }
}
