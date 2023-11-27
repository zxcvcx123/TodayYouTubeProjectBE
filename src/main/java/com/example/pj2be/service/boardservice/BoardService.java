package com.example.pj2be.service.boardservice;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.mapper.boardmapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {

    private final BoardMapper boardMapper;


    // 게시판 작성 기능
    // TODO: 사용자 세션 받아서 board.setWriter 해주기
    public boolean boardWrite(BoardDTO board) {
        
        // 테스트 데이터 넣기
        board.setWriter("testadmin");
        board.setCategory("C001");

        return boardMapper.boardWrite(board) == 1;

    }

    // 게시글 불러오기
    public List<BoardDTO> getBoardById(Integer id){

        return boardMapper.getBoardById(id);
    }



    // 게시글 수정
    public void updateBoardById(BoardDTO board, Integer id) {

        board.setId(id);
        boardMapper.updateBoardById(board);

    }

    // 게시글 삭제

    public void deleteBoardById(Integer id) {
        // 진짜 삭제할건지 감출건지 고민하기
    }

}
