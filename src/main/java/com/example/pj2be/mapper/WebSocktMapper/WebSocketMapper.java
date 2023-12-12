package com.example.pj2be.mapper.WebSocktMapper;

import com.example.pj2be.domain.alarm.AlarmDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebSocketMapper {

    @Update("""
            UPDATE socket
            SET testlike = #{num}
            WHERE id = 1
            """)
    void testLike(Integer num);

    @Select("""
            SELECT testlike FROM socket
            WHERE id = #{i}
            """)
    Map<String, Object> gettestLike(Integer i);

    @Insert("""
            INSERT INTO alarm (
                sender_member_id,
                receiver_member_id,
                alarm_category,
                board_id
                )
                VALUES
                (
                #{sender_member_id},
                #{receiver_member_id},
                #{alarm_category},
                #{board_id}
                )
            """)
    int commentAlarmSend(AlarmDTO alarmDTO);


    @Select("""
            SELECT
                a.id AS id,
                a.sender_member_id AS sender_member_id,
                a.receiver_member_id AS receiver_member_id,
                a.board_id AS board_id,
                b.title AS board_title,
                a.alarm_category as alarm_category,
                a.is_alarm AS is_alarm,
                a.created_at AS created_at
            FROM alarm a LEFT JOIN board b
                ON a.board_id = b.id
            WHERE receiver_member_id = #{receiver_member_id}
            ORDER BY a.id DESC;
            """)
    List<AlarmDTO> getCommentAlarmContent(AlarmDTO alarmDTO);
    
    @Select("""
            SELECT COUNT(id)
            FROM alarm 
            WHERE receiver_member_id = #{receiver_member_id} AND is_alarm = 0
            """)
     Integer getAlarmCount(String receiver_member_id);

    @Update("""
            UPDATE alarm
            SET is_alarm = 1
            WHERE id = #{id}
            """)
    void readAlarm(Integer id);

    @Update("""
            UPDATE youtube.alarm
            SET is_alarm = 1
            WHERE receiver_member_id = #{userId} AND is_alarm = 0
            """)
    void readAllAlarm(String userId);

    @Delete("""
            DELETE FROM alarm
            WHERE id = #{id}
            """)
    void deleteAlarm(Integer id);

    @Delete("""
            DELETE FROM alarm
            WHERE receiver_member_id = #{userId}
            """)
    void deleteAllAlarm(String userId);
}
