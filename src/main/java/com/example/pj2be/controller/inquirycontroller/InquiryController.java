package com.example.pj2be.controller.inquirycontroller;

import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.service.inquiryservice.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService service;

    @GetMapping("list")
    public List<InquiryDTO> list(){
        return service.list();

    }

    @PostMapping("write")
    public ResponseEntity write(@RequestBody InquiryDTO dto) {

//        로그인 객체 되면 수정
        dto.setInquiry_member_id("testadmin");

        if (!service.validate(dto)) {
            return ResponseEntity.badRequest().build();
        }
        service.add(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public InquiryDTO view(@PathVariable Integer id) {
        InquiryDTO dto = new InquiryDTO();
        dto.setInquiry_member_id("testadmin");

        return service.get(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {

        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("edit")
    public ResponseEntity edit(@RequestBody InquiryDTO dto) {
        dto.setInquiry_member_id("testadmin");

        service.update(dto);
        return ResponseEntity.ok().build();
    }
}
