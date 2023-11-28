package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.service.memberservice.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService service;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberDTO memberDTO,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            service.signup(memberDTO);
         return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    // 중복 체크 시작
    // 아이디
    @GetMapping(value = "/signup/check", params = "member_id")
    public ResponseEntity<?> checkMemberId(@RequestParam Optional<String> member_id){
        System.out.println("MemberController.checkMemberId");
        // null 여부
        if(member_id.isPresent()) {
            // 중복되는 경우
            if (service.getMemberId(member_id.get())) {
                // 409 : 리소스 충돌을 나타내는 상태코드
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다");
            } else {
                return ResponseEntity.ok().body("사용 가능한 아이디입니다");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다");
        }
    }
    // 닉네임
    @GetMapping(value = "/signup/check", params = "nickname")
    public ResponseEntity<?> checkNickName(@RequestParam  Optional<String> nickname){
        // null 여부
        if(nickname.isPresent()) {
            // 중복되는 경우
            if (service.getNickname(nickname.get())) {
                // 409 : 리소스 충돌을 나타내는 상태코드
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 별명입니다");
            } else {
                return ResponseEntity.ok().body("사용 가능한 별명입니다");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다");
        }
    }

    // 이메일
    @GetMapping(value = "/signup/check", params = "email")
    public ResponseEntity<?> checkEmail(@RequestParam  Optional<String> email){
        // null 여부
        if(email.isPresent()) {
            // 중복되는 경우
            if (service.getEmail(email.get())) {
                // 409 : 리소스 충돌을 나타내는 상태코드
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다");
            } else {
                return ResponseEntity.ok().body("등록 가능한 이메일입니다");
            }
        } else {
            return ResponseEntity.badRequest().body("잘못된 요청입니다");
        }
    }

    // 중복 체크 끝

}
