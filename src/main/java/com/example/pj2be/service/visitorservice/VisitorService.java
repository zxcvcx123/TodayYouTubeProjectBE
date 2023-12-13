package com.example.pj2be.service.visitorservice;

import com.example.pj2be.mapper.visitormapper.VisitorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorMapper visitorMapper;

    public void visitorCount(String clientIp, String member_id) {

        System.out.println("member_id = " + member_id);

        // 30분 전에 member_id로 찍힌 방문 기록이 있는지 조회
        int countRecentVisit = visitorMapper.countRecentVisit(member_id);
        System.out.println("countRecentVisit = " + countRecentVisit);

        // 30분 전에 찍힌 기록이 없다면 insert 수행
        if (countRecentVisit == 0) {
            visitorMapper.visitorInsert(clientIp, member_id);
        }

    }

    public Integer getVisitorCount() {
        visitorMapper.visitorCountToday();
        visitorMapper.visitorCountMonthlyLastYear();
        return visitorMapper.visitorCountAll();
    }
}
