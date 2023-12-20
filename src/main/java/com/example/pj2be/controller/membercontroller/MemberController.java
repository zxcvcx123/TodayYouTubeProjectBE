package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.config.jwt.JwtAuthenticationFilter;
import com.example.pj2be.config.security.AESEncryption;
import com.example.pj2be.config.security.KeyManager;
import com.example.pj2be.config.security.SecurityUtil;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.member.MemberLoginDTO;
import com.example.pj2be.domain.member.MemberRole;
import com.example.pj2be.domain.member.MemberUpdateDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyCommentDTO;
import com.example.pj2be.service.memberservice.MemberLoginService;
import com.example.pj2be.service.memberservice.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.example.pj2be.config.security.AESEncryption.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
     private final MemberLoginService memberLoginService;
     private final KeyManager keyManager;
    //  로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        log.info("login controller 실행됨");

        String member_id = memberLoginDTO.getMember_id();
        String password = memberLoginDTO.getPassword();

        if(!bindingResult.hasErrors()){
            Map<String, Object> jwtTokenAuthentication = memberService.login(member_id, password);
            // 권한 정보
            String tmp = jwtTokenAuthentication.get("authentication").toString();
            String Auth = String.valueOf(tmp.substring(1, tmp.length()-1));

            if( Auth.equals(MemberRole.GENERAL_MEMBER.getValue() )|| Auth.equals(MemberRole.ADMIN.getValue()) ){
                // TODO: 추후 권한에 따른 로직 설정
                if(memberService.setTokenInHttpOnlyCookie(jwtTokenAuthentication, response)) {
                    memberService.deleteMemberLoginInfoForValid(member_id);
                    memberService.setMemberLoginInfoForValid(member_id, password);
                    return ResponseEntity.ok().build();
                }
            }
            else if (Auth.equals(MemberRole.SUSPENSIONMEMBER.getValue())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            } else if (Auth.equals(MemberRole.WITHDRAWAL.getValue())) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
            }

        }
        return ResponseEntity.internalServerError().build() ;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    HttpServletResponse response){
        if(memberLoginService.resolveExpiredJwtCookie(request, response)){
            MemberDTO memberDTO = validateCookies(request);
            memberService.deleteMemberLoginInfoForValid(memberDTO.getMember_id());
            return ResponseEntity.ok().build();
        };
        return ResponseEntity.internalServerError().build();
    }


//    // 로그인 유지
    @PostMapping("/loginProvider")
    public ResponseEntity<MemberDTO> loginProvider(HttpServletRequest request, HttpServletResponse response) {
        MemberDTO memberDTO = validateCookies(request);
        Map<String, Object> token = getJwtFromCookie(request);
        String access = (String) token.get("jwtAccess");
        String refresh = (String) token.get("jwtRefresh");
        String accessToken = resolveToken(access);
        try {
            if (memberService.isTokenExpired(accessToken)) {
                if (!memberService.isTokenExpired(refresh)) {
                    try {
                        if (memberDTO.getMember_id() != null) {
                            Map<String, Object> jwtTokenAuthentication = memberService.login(memberDTO.getMember_id(), memberDTO.getPassword());
                            memberDTO.setPassword(null);
                            memberService.setTokenInHttpOnlyCookie(jwtTokenAuthentication, response);
                        }
                    } catch (NullPointerException e) {

                    }
                } else {
                    if (memberLoginService.resolveExpiredJwtCookie(request, response)) {
                        if (memberDTO.getMember_id() != null) {
                            memberService.deleteMemberLoginInfoForValid(memberDTO.getMember_id());
                            memberDTO.setMember_id(null);
                        }
                    }

                }
            }
        }catch (Exception e){}
        try{
            if(memberDTO != null ){
                memberDTO.setPassword(null);
                return ResponseEntity.ok().body(memberDTO);
            }
        }catch (NullPointerException e){
            memberDTO.setPassword(null);
        }
        memberLoginService.resolveExpiredJwtCookie(request, response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private String resolveToken(String token){;
        if(StringUtils.hasText(token) && token.startsWith("Bearer")){
            return token.substring(7);
        }
        return null;
    }
    private Map<String, Object> getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Object> jwt = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtAccess".equals(cookie.getName())) {
                    jwt.put("jwtAccess",cookie.getValue());
                }
                if("jwtRefresh".equals(cookie.getName())){
                    jwt.put("jwtRefresh", cookie.getValue());
                }
            }
            return jwt;
        }
        return null;
    }
    private MemberDTO validateCookies(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("_mi".equals(cookie.getName())) {
                        SecretKey key = keyManager.getSecretKey();
                        String memberId = AESEncryption.decrypt(cookie.getValue(), key);
                        MemberDTO memberDTO = memberLoginService.getLoginInfo(memberId);
                        String password =AESEncryption.decrypt(memberService.getMemberLoginValidInfo(memberId), key);
                        memberDTO.setPassword(password);
                        return memberDTO;

                    }
                }
            }
        }catch (Exception e){
        }
        return null;
    }

        // 회원 정보
    @GetMapping("/info")
    public ResponseEntity info(@RequestBody String member_id){
        // SecurityContext에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());

        // 인증되지 않은 경우
        if (authentication.getPrincipal() == member_id) {
            System.out.println("인증된 사용자 = "+ authentication.getPrincipal());
            return ResponseEntity.ok().build();
        }else if(authentication.getPrincipal() == "anonymousUser") {
            System.out.println("인증되지 않은 사용자");
            System.out.println(authentication.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/withdrawal")
    public ResponseEntity withdrawal(@RequestBody MemberUpdateDTO memberUpdateDTO){
        String memberId = memberUpdateDTO.getMember_id();
        if(memberService.withdrawalMember(memberId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    // 테스트
    @PostMapping("/test")
    public String test() {

        return SecurityUtil.getCurrentMemberId();
    }

}
