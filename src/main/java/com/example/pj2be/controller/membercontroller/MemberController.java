package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.MemberDTO;
import com.example.pj2be.service.memberservice.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService service;
    @PostMapping("/signup")
    public void signup(@RequestBody MemberDTO memberDTO){
        System.out.println("memberDTO = " + memberDTO);
        service.signup(memberDTO);
    }
}
