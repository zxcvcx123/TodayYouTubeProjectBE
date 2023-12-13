package com.example.pj2be.controller.votecontroller;

import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.service.voteservice.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    // 투표 게시글 등록
    @PostMapping("/add")
    public void voteBoardWrite(VoteDTO voteDTO) {

        System.out.println("voteDTO = " + voteDTO);

        voteService.add(voteDTO);
    }

    // 투표 게시글 리스트 보기
    @GetMapping("/list")
    public Map<String, Object> voteBoardList(@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
                                             @RequestParam(value = "k", required = false) String k,
                                             PageDTO pageDTO) {

        return voteService.list(pageDTO, p, k);
    }


    // 투표 게시글 보기
    @GetMapping("/id/{id}")
    public VoteDTO voteBoardView(@PathVariable Integer id) {
        System.out.println("투표 게시글: " + id);
        return voteService.view(id);


    }


}
