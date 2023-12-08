package com.example.pj2be.domain.alarm;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmDTO {


    private Integer id;
    private String sender_member_id; // 보낸이
    private String receiver_member_id; // 받는이
    private String alarm_category; // 알람 타입 (공지, 댓글 ...)
    private String board_id;
    private String comment_id;
    private String answer_id;
    private boolean is_alarm; // 알람 읽은 여부
    private LocalDateTime created_at; // 알람 시간

    public String getAgo() {

        return ChangeTimeStamp.getAgo(created_at);
    }


}
