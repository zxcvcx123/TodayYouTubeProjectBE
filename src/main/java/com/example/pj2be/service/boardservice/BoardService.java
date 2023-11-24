package com.example.pj2be.service.boardservice;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.mapper.boardmapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {

    private final BoardMapper boardMapper;

    // 게시글 작성
    public void save(BoardDTO board) {
        boardMapper.insert(board);

    }

    // 게시글 보기
    public BoardDTO get(Integer id) {
        BoardDTO board = boardMapper.selectById(id);

        return board;
    }
}
