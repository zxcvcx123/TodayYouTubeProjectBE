package com.example.pj2be.service.visitorservice;

import com.example.pj2be.mapper.visitormapper.VisitorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorMapper visitorMapper;

    public void visitorCount(String clientIp, String member_id) {

        visitorMapper.visitorInsert(clientIp, member_id);

    }
}
