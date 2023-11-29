package com.example.pj2be.controller.inquirycontroller;

import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.service.inquiryservice.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
