package com.example.pj2be.service.websocketservice;

import com.example.pj2be.domain.alarm.AlarmDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.mapper.WebSocktMapper.WebSocketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Array;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class WebSocketService {

    private final WebSocketMapper webSocketMapper;


    // 첫 접속시 알람 목록 가져오기
    public List<AlarmDTO> getAlarmList(AlarmDTO alarmDTO) {

        alarmDTO.setAlarm_category("ac002");
        // 알람온거 확인
        List<AlarmDTO> alarmList = webSocketMapper.getCommentAlarmContent(alarmDTO);

        return alarmList;
    }

    // 누군가 알림 보낼때마다 알림 전체리스트 가져오기
    public List<AlarmDTO> commentAlarmSend(AlarmDTO alarmDTO) {

        alarmDTO.setAlarm_category("ac002");
        // 알람 저장
        webSocketMapper.commentAlarmSend(alarmDTO);

        // 알람온거 확인
        List<AlarmDTO> alarmList = webSocketMapper.getCommentAlarmContent(alarmDTO);

        return alarmList;
    }

    // 알람 개수 가져오기
    public Integer getAlarmCount(String userId) {

        return webSocketMapper.getAlarmCount(userId);
    }

    // 알람 개별 읽기
    public void readAlarm(Integer id) {
        webSocketMapper.readAlarm(id);
    }

    // 알람 전부 읽기
    public void readAllAlarm(String userId) {

        webSocketMapper.readAllAlarm(userId);

    }

    // 알람 제거
    public void deleteAlarm(Map<String, Object> map) {

        Integer id = (Integer) map.get("id");
        String userId = (String) map.get("userId");
        String mode = (String) map.get("mode");

        if (mode.equals("ALL")) {
            // 알람 전부삭제
            webSocketMapper.deleteAllAlarm(userId);
        }
        if (mode.equals("ONE")) {
            // 알람 개별 삭제
            webSocketMapper.deleteAlarm(id);
        }

    }


    public void answerAlarmSend(AlarmDTO alarmDTO) {

        alarmDTO.setAlarm_category("ac003");

        webSocketMapper.answerAlarmSend(alarmDTO);

    }

    public void inquiryAlarmSend(AlarmDTO alarmDTO) {
        List<String> list = webSocketMapper.getAdminList(1);

        for (int i = 0; i < list.size(); i++) {

            alarmDTO.setAlarm_category("ac004");
            alarmDTO.setReceiver_member_id(list.get(i));
            System.out.println("운영자 아이디: " + list.get(i));
            System.out.println("반복문 테스트: " + alarmDTO);
            webSocketMapper.answerInquirySend(alarmDTO);

        }
    }
}

