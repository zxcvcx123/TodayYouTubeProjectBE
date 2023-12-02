package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.service.memberservice.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/member/info")
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    @GetMapping("/myBoardList")
    public List<BoardDTO> myBoardList(@RequestBody String member_id){
        List<BoardDTO> boardDTO = memberInfoService.getMyBoardList(member_id);

        return boardDTO;
    }
}
