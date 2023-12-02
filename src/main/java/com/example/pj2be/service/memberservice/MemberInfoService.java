package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.mapper.membermapper.MemberInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoMapper memberInfoMapper;

    public List<BoardDTO> getMyBoardList(String member_id) {
        return memberInfoMapper.getMyBoardList(member_id);
    }
}
