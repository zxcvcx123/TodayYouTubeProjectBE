package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;
    public void signup(MemberDTO memberDTO){

        mapper.signup(memberDTO);
    }
}
