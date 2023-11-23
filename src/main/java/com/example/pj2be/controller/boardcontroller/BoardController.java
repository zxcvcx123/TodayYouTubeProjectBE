package com.example.pj2be.controller.boardcontroller;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.service.boardservice.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    // 게시판 작성 기능
    @PostMapping("/write")
    public void boardWrite(@RequestBody BoardDTO board){

        // TODO: 세션 객체 받아서 작성자 파라미터에 넣어주기
        boardService.boardWrite(board);

        System.out.println("board = " + board);
    }

    // 게시글 불러오기
    @GetMapping("/{id}")
    public ResponseEntity getBoardById(@PathVariable("id")Integer id){

        List<BoardDTO> list = boardService.getBoardById(id);

        return ResponseEntity.ok(list);

    }

    // 게시글 수정

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteBoardById(@PathVariable("id")Integer id){
        boardService.deleteBoardById(id);
    }

}
