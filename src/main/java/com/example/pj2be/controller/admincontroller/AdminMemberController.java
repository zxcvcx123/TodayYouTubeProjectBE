package com.example.pj2be.controller.admincontroller;

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
}
