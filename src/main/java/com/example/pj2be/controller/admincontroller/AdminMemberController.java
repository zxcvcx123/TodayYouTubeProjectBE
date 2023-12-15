package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.AdminMemberDTO;
import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.page.PaginationDTO;
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
    public Map<String, Object> memberlist(@RequestParam (value = "p", defaultValue = "1") Integer page,
                                          @RequestParam(value = "mid", defaultValue = "") String mid) {
        return service.memberlist(page,mid);
    }

    @GetMapping("{member_id}")
    public Map<String, Object> memberInfo(@PathVariable String member_id,
                                          @RequestParam (value = "p", defaultValue = "1") Integer page,
                                          PaginationDTO paginationDTO) {
        return service.memberInfo(member_id, page, paginationDTO);
    }

    @PutMapping
    public void suspensionStart(@RequestBody SuspensionDTO dto) {
        System.out.println("dto = " + dto);

        service.memberSuspension(dto);
    }
}
