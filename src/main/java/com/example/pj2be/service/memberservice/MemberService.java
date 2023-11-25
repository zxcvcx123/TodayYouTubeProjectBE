package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입 로직
    public boolean signup(MemberDTO memberDTO){
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        return mapper.signup(memberDTO) == 1;
    }

    // 중복 체크 로직 시작
    public boolean getMemberId(String member_id) {
        return mapper.select_member_id(member_id) != null;
    }

    public boolean getNickname(String nickname) {
        return mapper.select_nickname(nickname) != null;
    }

    public boolean getEmail(String email) {
        return mapper.select_Email(email)!= null;
    }
    // 중복 체크 로직 끝
}
