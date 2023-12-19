package com.example.pj2be.controller.inquirycontroller;

import com.example.pj2be.domain.answer.AnswerDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.service.inquiryservice.InquiryService;
import com.example.pj2be.utill.MemberAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.pj2be.utill.MemberAccess.IsLoginMember;
import static com.example.pj2be.utill.MemberAccess.MemberChecked;

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
    public ResponseEntity delete(@PathVariable Integer id,
                                 @RequestBody InquiryDTO dto) {

        if (IsLoginMember(dto.getLogin_member_id())) {
            System.out.println("로그인 되어있음: " + dto.getLogin_member_id());
            if (MemberChecked(dto.getLogin_member_id(), dto.getInquiry_member_id()) == 0) {
                System.out.println("아이디랑 멤버아이디 같음");
                service.delete(id);
                System.out.println("수정 완료");
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        if (MemberChecked(dto.getLogin_member_id(), dto.getInquiry_category()) == 2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.internalServerError().build();

    }

    @PutMapping("edit")
    public ResponseEntity edit(@RequestBody InquiryDTO dto) {

        if (IsLoginMember(dto.getLogin_member_id())) {
            System.out.println("로그인 되어있음: " + dto.getLogin_member_id());
            if (MemberChecked(dto.getLogin_member_id(), dto.getInquiry_member_id()) == 0) {
                System.out.println("아이디랑 멤버아이디 같음");
                service.update(dto);
                System.out.println("수정 완료");
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        if (MemberChecked(dto.getLogin_member_id(), dto.getInquiry_category()) == 2) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("answer")
    public ResponseEntity answer(@RequestBody AnswerDTO dto) {

        if (!dto.getRole_name().equals("운영자")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.answerAdd(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("answer/edit")
    public ResponseEntity answerEdit(@RequestBody AnswerDTO dto) {

        if (!dto.getRole_name().equals("운영자")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.answerEdit(dto);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("answer/delete")
    public ResponseEntity deleteAnswer(@RequestBody AnswerDTO dto) {

        if (!dto.getRole_name().equals("운영자")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.answerDelete(dto);
        return ResponseEntity.ok().build();
    }

}
