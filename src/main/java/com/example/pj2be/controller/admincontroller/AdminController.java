package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.domain.page.PaginationDTO;
import com.example.pj2be.service.adminservice.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // 정지회원 관리 페이지
    @GetMapping("suspension")
    public Map<String, Object> suspensionList(@RequestParam (value = "p", defaultValue = "1") Integer page,
                                              PaginationDTO paginationDTO) {

        return adminService.getSuspensionList(page, paginationDTO);
    }

    // 정지사용자 정지안내 페이지
    @GetMapping("suspensionMessage/{member_id}")
    public SuspensionDTO  suspensionMember(@PathVariable (value = "member_id", required = false) String member_id) {

        System.out.println(member_id);
        return adminService.getSuspensionMember(member_id);
    }


    // 정지된 회원 정지해제
    @PutMapping("suspension")
    public ResponseEntity releaseSuspension(@RequestBody SuspensionDTO dto) {

        if (!dto.getRole_name().equals("운영자")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        adminService.updateSuspension(dto);
        return ResponseEntity.ok().build();
    }
}
