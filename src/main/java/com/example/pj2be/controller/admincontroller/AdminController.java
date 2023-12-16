package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("board")
    public Map<String, Object> boardDataList() {

        System.out.println("어드민 컨트롤러 보드데이터");

        return adminService.getBoardData();

    }

    @GetMapping("user")
    public Map<String, Object> userDataList() {

        System.out.println("어드민 컨트롤러 유저데이터");

        return adminService.getUserData();

    }

    @GetMapping("suspension")
    public Map<String, Object> suspensionList() {

        return adminService.getSuspensionList();
    }

    @PutMapping("suspension")
    public void releaseSuspension(@RequestBody SuspensionDTO dto) {

        adminService.updateSuspension(dto);
    }
}
