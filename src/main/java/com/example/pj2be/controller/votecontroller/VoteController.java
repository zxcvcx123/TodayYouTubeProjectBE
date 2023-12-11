package com.example.pj2be.controller.votecontroller;

import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.service.voteservice.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/add")
    public void voteWrite(VoteDTO voteDTO){

        System.out.println("voteDTO = " + voteDTO);

        voteService.add(voteDTO);
    }

}
