package com.example.pj2be.controller.votecontroller;

import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.service.voteservice.VoteCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class VoteCountController {

    private final VoteCountService voteCountService;

    @PutMapping("/votea")
    public void voteA(@RequestBody VoteCountDTO voteCountDTO) {
        System.out.println("voteCountDTO = " + voteCountDTO);


    }

    @PutMapping("/voteb")
    public void voteB(@RequestBody VoteCountDTO voteCountDTO) {
        System.out.println("voteCountDTO = " + voteCountDTO);
    }
}
