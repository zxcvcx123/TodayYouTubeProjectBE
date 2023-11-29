package com.example.pj2be.service.inquiryservice;

import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.mapper.inquirymapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class InquiryService {

    private final InquiryMapper mapper;

    public List<InquiryDTO> list() {
        return mapper.selectAll();

    }
}
