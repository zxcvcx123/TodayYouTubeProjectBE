package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.AdminMemberDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.service.adminservice.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final AdminMemberService service;

    @PostMapping("list")
    public Map<String, Object> memberlist(@RequestParam (value = "p", defaultValue = "1") Integer page) {
        return service.memberlist(page);
    }

    @GetMapping("{member_id}")
    public AdminMemberDTO memberInfo(@PathVariable String member_id) {
        return service.memberInfo(member_id);
    }
}
