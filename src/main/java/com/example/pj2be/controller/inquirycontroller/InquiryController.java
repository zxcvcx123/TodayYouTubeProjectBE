package com.example.pj2be.controller.inquirycontroller;

import com.example.pj2be.domain.answer.AnswerDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.domain.member.MemberDTO;
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

    @PostMapping("list")
    public Map<String, Object> list(@RequestParam(value = "p", defaultValue = "1") Integer page,
                                    @RequestBody InquiryDTO dto){

        return service.list(page, dto);

    }

    @PostMapping("write")
    public ResponseEntity write(@RequestBody InquiryDTO dto) {

//        로그인 객체 되면 수정

        if (!service.validate(dto)) {
            return ResponseEntity.badRequest().build();
        }
        service.add(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public InquiryDTO view(@PathVariable Integer id) {



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

    @PostMapping("answer")
    public ResponseEntity answer(@RequestBody AnswerDTO dto) {
        System.out.println("dto = " + dto);

        service.answerAdd(dto);
        return ResponseEntity.ok().build();
    }
}
