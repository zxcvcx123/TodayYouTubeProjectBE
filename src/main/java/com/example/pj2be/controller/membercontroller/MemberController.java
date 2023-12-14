package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.config.security.SecurityUtil;
import com.example.pj2be.domain.member.JwtToken;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.member.MemberLoginDTO;
import com.example.pj2be.domain.member.MemberRole;
import com.example.pj2be.service.memberservice.MemberLoginService;
import com.example.pj2be.service.memberservice.MemberService;
import com.example.pj2be.service.memberservice.MemberSignupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
     private final MemberLoginService memberLoginService;

    //  로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpServletResponse response) throws JsonProcessingException {
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
                return ResponseEntity.ok().body(jwtTokenAuthentication);
            };

        }
        return ResponseEntity.internalServerError().build() ;
    }

//    // 로그인 유지
    @PostMapping("/loginProvider")
    public ResponseEntity<MemberDTO> loginProvider(String member_id){
        try {
            if (member_id != null) {
                MemberDTO memberDTO = memberLoginService.getLoginInfo(member_id);

                return ResponseEntity.ok().body(memberDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
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
