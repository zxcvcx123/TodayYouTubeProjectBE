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

//       -------------------- 현재 날짜 기준---------------
        int sortTime = 0;
//        rankingTime = (현재날짜 - ?일) 동안 최다 추천수 받은 날짜
//        LocalDateTime rankingTime = LocalDateTime.now();
//
//        if (sort.equals("daily") || sort.equals("weekly")) {
//
//            if (sort.equals("daily")) {
//            sortTime = 1;
//        } else if (sort.equals("weekly")) {
//            sortTime = 7;
//        }
//            rankingTime = SeoulTime.minusDays(sortTime);
//
//        } else if (sort.equals("monthly")) {
//            sortTime = 1;
//            rankingTime = SeoulTime.minusMonths(sortTime);
//        }

        //        category가 all일 경우에 다른 mapper메소드 실행
//        if (c.equals("all")) {
//            map.put("otherBoardList", mapper.selectOtherByALl(rankingTime));
//            map.put("firstBoardList", mapper.selectFirstByAll(rankingTime));
//        } else {
//            map.put("otherBoardList", mapper.selectOtherByCategory(c, rankingTime));
//            map.put("firstBoardList", mapper.selectFirstByCategory(c, rankingTime));
//        }

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

        System.out.println("startDay = " + startDay);
        System.out.println("endDay = " + endDay);

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
