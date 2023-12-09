package com.example.pj2be.service.adminservice;

import com.example.pj2be.mapper.adminmapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

    public Map<String, Object> getBoardData() {
        Map<String, Object> map = new HashMap<>();

        map.put("boardList", adminMapper.getBoardData());

        System.out.println("어드민 서비스 보드데이터");

        return map;
    }
}
