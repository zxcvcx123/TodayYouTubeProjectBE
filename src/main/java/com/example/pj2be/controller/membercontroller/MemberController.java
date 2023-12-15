package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.config.jwt.JwtAuthenticationFilter;
import com.example.pj2be.config.security.SecurityUtil;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.member.MemberLoginDTO;
import com.example.pj2be.domain.member.MemberRole;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
     private final MemberLoginService memberLoginService;
    //  로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        log.info("login controller 실행됨");

        String member_id = memberLoginDTO.getMember_id();
        String password = memberLoginDTO.getPassword();

        if(!bindingResult.hasErrors()){
            Map jwtTokenAuthentication = memberService.login(member_id, password);
            // 권한 정보
            String tmp = jwtTokenAuthentication.get("authentication").toString();
            String Auth = String.valueOf(tmp.substring(1, tmp.length()-1));

            if( Auth.equals(MemberRole.GENERAL_MEMBER.getValue() )|| Auth.equals(MemberRole.ADMIN.getValue()) ){
                // TODO: 추후 권한에 따른 로직 설정
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

                String memberInfo = URLEncoder.encode( jwtTokenAuthentication.get("memberInfo").toString(),"UTF-8");
                Cookie memberId = new Cookie("_mi", memberInfo);
                memberId.setHttpOnly(true);
                memberId.setPath("/");
                memberId.setMaxAge(60*60*24);
                response.addCookie(memberId);
              return ResponseEntity.ok().build();
            }

        }
        return ResponseEntity.internalServerError().build() ;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtAccess".equals(cookie.getName())) {
                    memberLoginService.expireJwtCookie(response, "jwtAccess");
                }
                if ("_mi".equals(cookie.getName())) {
                    memberLoginService.expireJwtCookie(response, "_mi");
                }
                if ("jwtRefresh".equals(cookie.getName())) {
                    memberLoginService.expireJwtCookie(response, "jwtRefresh");
                }
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }

//    // 로그인 유지
    @PostMapping("/loginProvider")
    public ResponseEntity<MemberDTO> loginProvider(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("_mi".equals(cookie.getName())) {
                        MemberDTO memberDTO = memberLoginService.getLoginInfo(cookie.getValue());
                        return ResponseEntity.ok().body(memberDTO);
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    // 테스트
    @PostMapping("/test")
    public String test() {

        return SecurityUtil.getCurrentMemberId();
    }
}
