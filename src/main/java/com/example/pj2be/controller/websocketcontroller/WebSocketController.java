package com.example.pj2be.controller.websocketcontroller;

import com.example.pj2be.domain.alarm.AlarmDTO;
import com.example.pj2be.domain.like.BoardLikeDTO;
import com.example.pj2be.domain.socket.ChatDTO;
import com.example.pj2be.domain.socket.Greeting;
import com.example.pj2be.domain.socket.HelloMessage;
import com.example.pj2be.service.likeservice.BoardLikeService;
import com.example.pj2be.service.websocketservice.testLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final BoardLikeService boardLikeService;
    private final SimpMessagingTemplate simpMessagingTemplate; // 특정 유저에게 보내야해서 사용

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public ChatDTO greeting(ChatDTO chatDTO) throws Exception {
//        System.out.println("소켓테스트확인됨");
//        System.out.println(chatDTO);
//        System.out.println("아이디: " + chatDTO.getId());
//        System.out.println("내용: " + chatDTO.getChat());
//
//        return chatDTO;
//    }

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
        System.out.println("댓글 알림 userId: " + userId);
        System.out.println("댓글 내용 alarmDTO:" + alarmDTO);
        String toId = "abcd";
        Map<String, String> map = new HashMap<>();
        map.put("인사","안녕하세요");
        simpMessagingTemplate.convertAndSend("/queue/comment/alarm/" + toId, map);

    }

}
