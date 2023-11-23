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
    public ResponseEntity boardWrite(@RequestBody BoardDTO board){

        // TODO: 세션 객체 받아서 작성자 파라미터에 넣어주기
        // TODO: 권한, Http Status 상태 추가 작성
        if( boardService.boardWrite(board)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.internalServerError().build();
    }

    // 게시글 불러오기
    @GetMapping("/{id}")
    public ResponseEntity getBoardById(@PathVariable("id")Integer id){

        // TODO: 권한, Http Status 상태 추가 작성
        List<BoardDTO> list = boardService.getBoardById(id);

        return ResponseEntity.ok(list);

    }

    // 게시글 수정
    @PutMapping("/update/{id}")
    public void updateBoardById(@RequestBody BoardDTO board,
                                @PathVariable("id")Integer id){

        boardService.updateBoardById(board, id);

    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteBoardById(@PathVariable("id")Integer id){
        // TODO: 권한, Http Status 상태 추가 작성
        boardService.deleteBoardById(id);
    }

}
