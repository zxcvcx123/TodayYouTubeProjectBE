package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberMapper memberMapper;

    public MemberDTO getLoginInfo(String member_id) {
        return memberMapper.findLoginInfoByMemberId(member_id);
    }
}
