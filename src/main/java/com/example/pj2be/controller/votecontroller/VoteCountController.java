package com.example.pj2be.controller.votecontroller;

import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.mapper.WebSocktMapper.WebSocketMapper;
import com.example.pj2be.service.voteservice.VoteCountService;
import com.example.pj2be.service.websocketservice.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteCountController {

    private final WebSocketMapper webSocketMapper;
    private final WebSocketService webSocketService;
    private final VoteCountService voteCountService;
    private final SimpMessagingTemplate simpMessagingTemplate; // 특정 유저에게 보내야해서 사용

    @MessageMapping("/votea")
    public void voteA(@RequestBody VoteCountDTO voteCountDTO) {

        //voteCountService.addVoteA(voteCountDTO);
        System.out.println("voteCountDTO = " + voteCountDTO);
        String checked = "voteA";

//        VoteCountDTO check = voteCountService.voteCheck(voteCountDTO, checked);
        VoteCountDTO count = voteCountService.voteGetCount(voteCountDTO);
        simpMessagingTemplate.convertAndSend("/topic/voteresult", count);
    }

    @MessageMapping("/voteb")
    public void voteB(@RequestBody VoteCountDTO voteCountDTO) {
        //voteCountService.addVoteB(voteCountDTO);

        System.out.println("voteCountDTO = " + voteCountDTO);
        String checked = "voteB";

//        VoteCountDTO check = voteCountService.voteCheck(voteCountDTO, checked);
        VoteCountDTO count = voteCountService.voteGetCount(voteCountDTO);
        simpMessagingTemplate.convertAndSend("/topic/voteresult", count);
    }


}
