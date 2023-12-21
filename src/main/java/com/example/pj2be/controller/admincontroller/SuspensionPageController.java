package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suspensionMessage")
public class SuspensionPageController {

    private final AdminService adminService;

    // 정지사용자 정지안내 페이지
    @GetMapping("/{member_id}")
    public SuspensionDTO suspensionMember(@PathVariable(value = "member_id", required = false) String member_id) {

        System.out.println(member_id);
        return adminService.getSuspensionMember(member_id);
    }

}
