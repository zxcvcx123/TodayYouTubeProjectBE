package com.example.pj2be.service.memberservice;

import com.example.pj2be.config.jwt.JwtTokenProvider;
import com.example.pj2be.domain.member.JwtToken;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public Map<String, Object> login(String member_id, String password){
        log.info("login이 실행됨!!!");

        // UsernamePasswordAuthenticationToken 객체에 사용자 id와 비밀번호 저장를 저장
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member_id, password);
        System.out.println("MemberService의 authenticationToken이 실행됨!! " + authenticationToken );
        // 실제 검증
        // MemberSecurityService loadUserByUsername 호출해서 로그인 요청한 id와 비밀번호와 db에 저장된 정보와 검증을함
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 권한만 추출
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
        // JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        String accessToken = jwtToken.getAccessToken();
        String grantType = jwtToken.getGrantType();
        String refreshToken = jwtToken.getRefreshToken();
        System.out.println("MemberService의 jwtToken 실행됨!! " + jwtToken );

        Map<String, Object> jwtTokenAuthenticationMap = new HashMap<String, Object>();
        jwtTokenAuthenticationMap.put("accessToken", grantType + " " + accessToken);
        jwtTokenAuthenticationMap.put("refreshToken",  refreshToken);
        jwtTokenAuthenticationMap.put("authentication", authentication.getAuthorities());
        jwtTokenAuthenticationMap.put("memberInfo", member_id);
        return jwtTokenAuthenticationMap;
    }

    public boolean withdrawalMember(String memberId) {
        System.out.println("memberId = " + memberId);
        return memberMapper.withdrawalByMemberId(memberId);
    }
}
