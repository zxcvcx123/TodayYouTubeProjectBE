package com.example.pj2be.controller.admincontroller;

import com.example.pj2be.domain.admin.AdminMemberDTO;
import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.page.PaginationDTO;
import com.example.pj2be.service.adminservice.AdminMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final AdminMemberService service;

    // 회원목록 리스트 출력
    @PostMapping("list")
    public Map<String, Object> memberlist(@RequestParam (value = "p", defaultValue = "1") Integer page,
                                          @RequestParam(value = "mid", defaultValue = "") String mid) {
        return service.memberlist(page,mid);
    }

    // 회원정보 클릭시 출력
    @GetMapping("{member_id}")
    public Map<String, Object> memberInfo(@PathVariable String member_id,
                                          @RequestParam (value = "p", defaultValue = "1") Integer page,
                                          PaginationDTO paginationDTO) {
        return service.memberInfo(member_id, page, paginationDTO);
    }

    // memberinfodetail에서 회원정지버튼 누를때 실행
    @PutMapping
    public ResponseEntity suspensionStart(@RequestBody @Valid SuspensionDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        if (!dto.getRole_name().equals("운영자")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return service.memberSuspension(dto);

    }
}
