package com.example.pj2be.controller.boardcontroller;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.service.boardservice.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
