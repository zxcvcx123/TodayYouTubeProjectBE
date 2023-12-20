package com.example.pj2be.service.memberservice;

import com.example.pj2be.config.jwt.JwtTokenProvider;
import com.example.pj2be.config.security.AESEncryption;
import com.example.pj2be.config.security.KeyManager;
import com.example.pj2be.domain.member.JwtToken;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.net.URLEncoder;
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
    private final KeyManager keyManager;
    @Transactional
    public Map<String, Object> login(String member_id, String password){
        // UsernamePasswordAuthenticationToken 객체에 사용자 id와 비밀번호 저장를 저장
        System.out.println("--------------------login  실행됨!!");

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


    public boolean isTokenExpired(String token) {
        System.out.println("token = " + token);
        return jwtTokenProvider.validateAccessExpired(token);
    }

    public boolean setTokenInHttpOnlyCookie(Map<String, Object> jwtTokenAuthentication,  HttpServletResponse response)  {
        try {
            String memberId = jwtTokenAuthentication.get("memberInfo").toString();
            SecretKey key = keyManager.getSecretKey();
            String encryptMemberId = AESEncryption.encrypt(memberId, key);


            String accessToken = URLEncoder.encode( jwtTokenAuthentication.get("accessToken").toString(),"UTF-8");
            Cookie jwtAccess = new Cookie("jwtAccess",accessToken);
            jwtAccess.setHttpOnly(true);
            jwtAccess.setPath("/");
            jwtAccess.setMaxAge(60 * 60* 24);
            response.addCookie(jwtAccess);

            String refreshToken = URLEncoder.encode( jwtTokenAuthentication.get("refreshToken").toString(),"UTF-8");
            Cookie jwtRefresh = new Cookie("jwtRefresh",refreshToken);
            jwtRefresh.setHttpOnly(true);
            jwtRefresh.setPath("/");
            jwtRefresh.setMaxAge(60 * 60* 24);
            response.addCookie(jwtRefresh);

            String memberInfo = encryptMemberId;
            Cookie _mi = new Cookie("_mi", memberInfo);
            _mi.setHttpOnly(true);
            _mi.setPath("/");
            _mi.setMaxAge(60*60*24);
            response.addCookie(_mi);


        return true;
        }catch (Exception e){

        }
        return false;
    }

    public void setMemberLoginInfoForValid(String memberId, String password) {
        try {
            SecretKey key = keyManager.getSecretKey();
            String encryptPassword = AESEncryption.encrypt(password, key);
            memberMapper.updateMemberLoginInfoForValid(memberId, encryptPassword);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMemberLoginValidInfo(String memberId) {
        return memberMapper.getMemberLoginValidInfo(memberId);
    }

    public boolean deleteMemberLoginInfoForValid(String memberId) {
        return memberMapper.deleteMemberLoginInfoForValid(memberId);
    }
}
