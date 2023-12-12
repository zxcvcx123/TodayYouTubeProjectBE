package com.example.pj2be.controller.websocketcontroller;

import com.example.pj2be.domain.alarm.AlarmDTO;
import com.example.pj2be.domain.like.BoardLikeDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.socket.ChatDTO;
import com.example.pj2be.mapper.WebSocktMapper.WebSocketMapper;
import com.example.pj2be.service.inquiryservice.InquiryService;
import com.example.pj2be.service.likeservice.BoardLikeService;
import com.example.pj2be.service.websocketservice.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketMapper webSocketMapper;
    private final WebSocketService webSocketService;
    private final BoardLikeService boardLikeService;
    private final SimpMessagingTemplate simpMessagingTemplate; // 특정 유저에게 보내야해서 사용


    /* ========== 채팅 ========== */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatDTO greeting(ChatDTO chatDTO) throws Exception {
        System.out.println("소켓테스트확인됨");
        System.out.println(chatDTO);
        System.out.println("아이디: " + chatDTO.getId());
        System.out.println("내용: " + chatDTO.getChat());

        return chatDTO;
    }
    /* ========================= */


    /* ========== 좋아요 ========== */
    @MessageMapping("/like/")
    @SendTo("/topic/like")
    public Map<String, Object> like(BoardLikeDTO boardLikeDTO) {

        return boardLikeService.getBoardLike(boardLikeDTO);

    }


    // 특정 누군가에게만 받고 특정 누군가에게만 전달 가능
    // 웹소켓 통신의 @PathVariable => @DestinationVariable 으로
    @MessageMapping("/like/add/{userId}")
    @SendTo("/queue/like/{userId}")
    public Map<String, Object> addLike(@DestinationVariable String userId,
                                       BoardLikeDTO boardLikeDTO) {

        System.out.println("특정 웹소켓: " + boardLikeDTO);
        System.out.println("특정 웹소켓 계정: " + userId);
        Map<String, Object> map = new HashMap<>();
        map.put("like", boardLikeService.boardLike(boardLikeDTO).get("like"));
        map.put("memberId", boardLikeDTO.getMember_id());
        System.out.println("보내는 데이터: " + map);
        return map;
    }
    /* ========================== */


    /* ========== 알람 ========== */
    // 댓글알람
    @MessageMapping("/comment/sendalarm/{userId}")
    public void addCommentAlarm(@DestinationVariable String userId,
                                @RequestBody AlarmDTO alarmDTO) {

        // 알림 최신화
        List<AlarmDTO> list = webSocketService.commentAlarmSend(alarmDTO);

        // 알림 최신 개수
        Integer count = webSocketService.getAlarmCount(alarmDTO.getReceiver_member_id());

        // 받을 사람 ID
        String toId = alarmDTO.getReceiver_member_id();

        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/" + toId, list);

    }

    // 댓글알람개수
    @MessageMapping("/comment/sendalarm/count/{userId}")
    public void receiverCommentAlarmCount(@DestinationVariable String userId,
                                          @RequestBody AlarmDTO alarmDTO) {

        // 알림 최신 개수
        Integer count = webSocketService.getAlarmCount(alarmDTO.getReceiver_member_id());

        // 받을 사람 ID
        String toId = alarmDTO.getReceiver_member_id();

        // 받을 소켓주소 + ID로 데이터 넘겨주기
        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/count/" + toId, count);

    }

    // 초기에 알람목록 가져오기 (ajax)
    @PostMapping("api/websocket/alarmlist")
    public List<AlarmDTO> getAlarmList(@RequestBody Map<String, String> map) {

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setReceiver_member_id(map.get("userId"));

        return webSocketService.getAlarmList(alarmDTO);
    }

    // 알람 개수 (ajax)
    @PostMapping("api/websocket/alarmcount")
    public Integer getAlarmCount(@RequestBody Map<String, String> map) {

        AlarmDTO alarmDTO = new AlarmDTO();

        return webSocketService.getAlarmCount(map.get("userId"));
    }

    // 알람 개별 읽기 (ajax)
    @PostMapping("api/alarmread")
    public void readAlarm(@RequestBody Map<String, Integer> map) {

        // 알람 개별 읽기
        webSocketService.readAlarm(map.get("id"));

    }

    // 알람 모두 읽기
    @MessageMapping("/comment/alarm/allread/{userId}")
    @SendTo("/queue/comment/alarm/{userId}")
    public void readAllAlarm(@DestinationVariable String userId) {

        // 알람 전부읽기
        webSocketService.readAllAlarm(userId);

        // 코드 재활용 위해 DTO 형식으로 보내줘야 해서 선언
        AlarmDTO alarmDTO = new AlarmDTO();

        // 알람 목록 가져오기
        alarmDTO.setReceiver_member_id(userId);
        List<AlarmDTO> list = webSocketService.getAlarmList(alarmDTO);

        // 알람 수 가져오기
        // 알림 최신 개수
        Integer count = webSocketService.getAlarmCount(userId);

        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/" + userId, list);
        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/count/" + userId, count);
    }

    // 알람 삭제
    @MessageMapping("/comment/alarm/delete")
    public void deleteAlarm(@RequestBody Map<String, Object> map) {

        webSocketService.deleteAlarm(map);

        String userId = (String) map.get("userId");

        // 코드 재활용 위해 DTO 형식으로 보내줘야 해서 선언
        AlarmDTO alarmDTO = new AlarmDTO();

        // 알람 목록 가져오기
        alarmDTO.setReceiver_member_id(userId);
        List<AlarmDTO> list = webSocketService.getAlarmList(alarmDTO);

        // 알람 수 가져오기
        // 알림 최신 개수
        Integer count = webSocketService.getAlarmCount(userId);

        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/" + userId, list);
        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/count/" + userId, count);

    }

    @MessageMapping("/answer/sendalarm")
    public void addAnswerAlarm(@RequestBody AlarmDTO alarmDTO) {
        System.out.println("alarmDTO = " + alarmDTO);

        webSocketService.answerAlarmSend(alarmDTO);

        // 알람 받는이 설정
        String toId = alarmDTO.getReceiver_member_id();
        System.out.println("toId1 = " + toId);

        // 알람 수 최신화 데이터
        Integer count = webSocketService.getAlarmCount(alarmDTO.getReceiver_member_id());
        System.out.println("alarmDTO.getReceiver_member_id() = " + alarmDTO.getReceiver_member_id());

        // 알람목록 최신화 데이터
        List<AlarmDTO> list = webSocketService.getAlarmList(alarmDTO);


        simpMessagingTemplate.convertAndSend("/queue/answer/alarm/" + toId, list);
        simpMessagingTemplate.convertAndSend("/queue/answer/alarm/count/" + toId, count);
    }

    @MessageMapping("/inquiry/sendalarm")
    public void addInquiryAlarm(@RequestBody AlarmDTO alarmDTO,
                                @RequestBody MemberDTO memberDTO) {
        webSocketService.inquiryAlarmSend(alarmDTO);
        System.out.println("alarmDTO = " + alarmDTO);

        List<String> idList = webSocketMapper.getAdminList(1);



        for (String toId : idList) {
            alarmDTO.setReceiver_member_id(toId);
            List<AlarmDTO> list = webSocketService.getAlarmList(alarmDTO);
            System.out.println("list = " + list);

            Integer count = webSocketService.getAlarmCount(toId);
            System.out.println("count = " + count);


            simpMessagingTemplate.convertAndSend("/queue/inquiry/alarm/" + toId, list);
            simpMessagingTemplate.convertAndSend("/queue/inquiry/alarm/count/" + toId, count);
        }
    }

}


