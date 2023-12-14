package com.example.pj2be.controller.votecontroller;

import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.service.voteservice.VoteService;
import com.example.pj2be.utill.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.pj2be.utill.MemberAccess.IsLoginMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vote")
public class VoteController {

    private final VoteService voteService;

    // 투표 게시글 등록
    @PostMapping("/add")
    public ResponseEntity voteBoardWrite(VoteDTO voteDTO) {

        System.out.println("voteDTO = " + voteDTO);

        // 글쓰기 버튼 클릭했는데, 로그인 아이디가 null로 오는 것 검증, 비로그인 사용자는 401 반환
        if (!IsLoginMember(voteDTO.getVote_member_id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            voteService.add(voteDTO);
            return ResponseEntity.ok().build();
        }


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

    @PostMapping("history")
    public VoteCountDTO voteHistory(@RequestBody VoteCountDTO voteCountDTO) {
        System.out.println("체크드 실행");
        return voteService.voteHistory(voteCountDTO);
    }


}
