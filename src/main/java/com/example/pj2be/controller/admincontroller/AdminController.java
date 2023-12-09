package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("board")
    public Map<String, Object> list() {

        System.out.println("어드민 컨트롤러 보드데이터");

        return adminService.getBoardData();

    }
}
