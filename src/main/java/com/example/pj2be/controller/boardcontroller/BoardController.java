package com.example.pj2be.controller.boardcontroller;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.service.boardservice.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

     // 게시글 작성
    @PostMapping("add")
    public void add(BoardDTO board) {
        boardService.save(board);
    }

    // 게시글 목록
    @GetMapping("list")
    public List<BoardDTO> list() {
        return boardService.list();
    }

    // 게시글 보기
    @GetMapping("id/{id}")
    public BoardDTO view(@PathVariable Integer id) {
        return boardService.get(id);
    }

    // 게시글 수정
    @PutMapping("edit")
    public void edit(@RequestBody BoardDTO board) {
        boardService.update(board);
    }



}
