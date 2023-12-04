package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.service.memberservice.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member/info")
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    @GetMapping("/myBoardList")
    public Map<String, Object> myBoardList(@RequestParam("member_id") String member_id,
                                           @RequestParam("ob") String categoryOrdedBy,
                                           @RequestParam("ct") String categoryTopics
    ){
        System.out.println(member_id);
        System.out.println(categoryOrdedBy);
        System.out.println(categoryTopics);
        Map<String, Object> myBoardList = memberInfoService.getMyBoardList(member_id, categoryTopics, categoryOrdedBy);

        return myBoardList;
    }
}
