package com.example.pj2be.service.mainservice;

import com.example.pj2be.mapper.mainmapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class MainService {

    private final MainMapper mapper;


    public Map<String, Object> select(String c, String sort) {
        LocalDateTime SeoulTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Map<String, Object> map = new HashMap<>();


        // ---------------현재날짜 속한 요일 기준--------------
        DayOfWeek dayOfWeek = SeoulTime.getDayOfWeek();
        int weekNumber = dayOfWeek.getValue();
        LocalDateTime startDay = SeoulTime;
        LocalDateTime endDay = SeoulTime;



            if (sort.equals("daily")) {
                startDay = SeoulTime.with(LocalTime.MIDNIGHT);
                endDay = startDay.plusDays(1);

            } else if (sort.equals("weekly")) {
                startDay = SeoulTime.with(LocalTime.MIDNIGHT).minusDays(weekNumber);
                endDay = startDay.plusDays(6);
        }

         else if (sort.equals("monthly")) {
            startDay = SeoulTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIDNIGHT);
            endDay = SeoulTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MIDNIGHT);
        }


        if (c.equals("all")) {
            map.put("otherBoardList", mapper.selectOtherByALl2(startDay, endDay));
            map.put("firstBoardList", mapper.selectFirstByAll2(startDay, endDay));
        } else {
            map.put("otherBoardList", mapper.selectOtherByCategory2(c, startDay, endDay));
            map.put("firstBoardList", mapper.selectFirstByCategory2(c, startDay, endDay));
        }



        return map;
    }
}
