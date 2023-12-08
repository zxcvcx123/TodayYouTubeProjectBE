package com.example.pj2be.service.websocketservice;

import com.example.pj2be.domain.alarm.AlarmDTO;
import com.example.pj2be.mapper.WebSocktMapper.WebSocketMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        List<AlarmDTO> alarmList= webSocketMapper.getCommentAlarmContent(alarmDTO);

        return alarmList;
    }

    // 알람 개수 가져오기
    public Integer getAlarmCount(String userId){

        return webSocketMapper.getAlarmCount(userId);
    }


    public void readAlarm(Integer id) {
        webSocketMapper.readAlarm(id);
    }
}
