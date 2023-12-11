package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyDTO;
import com.example.pj2be.service.memberservice.MemberMinihomeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member/minihomepy")
@RequiredArgsConstructor
public class MemberMinihomepyController {
    private final MemberMinihomeyService service;


 @GetMapping("member_id/{member_id}")
 public MemberDTO getMemberMinihomepyInfo(@PathVariable String member_id){

     MemberDTO member = service.getMemberMinihomepyInfo(member_id);

     return member;
 }

 @GetMapping("boardlist/member_id/{member_id}")
 public Map<String, Object> getMiniHomepyBoardList(@PathVariable String member_id){
     Map<String, Object> topBoardList = service.getMiniHomepyBoardList(member_id);
     System.out.println("member_id = " + member_id);
     System.out.println("Controller topBoardList = " + topBoardList);
    return topBoardList;
 }

    @GetMapping("/boardlist/all")
    public Map<String, Object> myBoardList(@RequestParam("member_id") String member_id,
                                           @RequestParam("ob") String categoryOrdedBy
    ){

        Map<String, Object> allBoardList = service.getAllBoardList(member_id, categoryOrdedBy);

        return allBoardList;
    }

    @GetMapping("member_id/info/{member_id}")
    public MiniHomepyDTO getMinihomepyInfo(@PathVariable String member_id){
        MiniHomepyDTO miniHomepyDTO = service.getMinihomepyInfo(member_id);
        System.out.println("miniHomepyDTO = " + miniHomepyDTO);
     return miniHomepyDTO;
    }

    @PatchMapping("/edit/introduce")
    public ResponseEntity updateIntroduce(@RequestBody MiniHomepyDTO miniHomepyDTO){
     String member_id = miniHomepyDTO.getMember_id();
     String introduce = miniHomepyDTO.getIntroduce();
    if(service.updateIntroduce(member_id, introduce)){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping("/edit/bgm")
    public ResponseEntity updateBgm(@RequestBody MiniHomepyDTO miniHomepyDTO){
     String member_id = miniHomepyDTO.getMember_id();
     String bgm_link = miniHomepyDTO.getBgm_link();
 if(service.updateBgm(member_id, bgm_link)){
     return ResponseEntity.status(HttpStatus.OK).build();
 }
 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
 }

    @PostMapping("/view")
    public ResponseEntity addHomepyVisiterView(@RequestBody MiniHomepyDTO miniHomepyDTO){
        if(service.addMiniHomepyVisiterViewByMemberId(miniHomepyDTO)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.noContent().build();
        }
    }
}
