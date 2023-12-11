package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import com.example.pj2be.mapper.membermapper.MiniHomepyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class MemberSignupService {
    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final MiniHomepyMapper miniHomepyMapper;

    // 회원 가입 로직
    public boolean signup(MemberDTO memberDTO) throws AccessDeniedException {
        if(memberDTO.getRole_id() == 2) {
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            System.out.println("signup 서비스 실행!");
        } else {
            throw new AccessDeniedException("가입 불가한 권한입니다");
        }
        boolean result = mapper.signup(memberDTO);
        System.out.println("result = " + result);
        boolean result2 = miniHomepyMapper.createUserHomepy(memberDTO.getMember_id());
        System.out.println("result2 = " + result2);
        return result;
    }

    // 중복 체크 로직 시작
    public boolean getMemberId(String member_id) {
        System.out.println("mapper.select_member_id(member_id = " + mapper.select_member_id(member_id));
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
