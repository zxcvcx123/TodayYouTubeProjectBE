package com.example.pj2be.service.boardservice;

import com.example.pj2be.domain.BoardDTO;
import com.example.pj2be.mapper.boardmapper.BoardMapper;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;


    // 게시글 작성
    public void save(BoardDTO board, MultipartFile[] files) throws Exception {
        boardMapper.insert(board);

        if(files != null) {
            for (MultipartFile file : files) {
                fileService.s3Upload(file, board.getId());
            }
        }

    }

    // 게시글 리스트, 페이징
    public Map<String, Object> list() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();


        // 페이징 필요한 것들
        // 전체페이지, 보여줄페이지 수, 왼쪽끝페이지, 오른쪽끝페이지
        int countAll;
        countAll = boardMapper.selectAllpage();
        int slice = 5;
        int lastPageNumber = countAll / 5;



        map.put("boardList", boardMapper.selectAll());
        map.put("pageInfo", pageInfo);

        return map;
    }

    // 게시글 보기
    public BoardDTO get(Integer id) {
        BoardDTO board = boardMapper.selectById(id);

        return board;
    }

    // 게시글 수정
    public void update(BoardDTO board) {


        boardMapper.update(board);

    }




    // 게시글 삭제 (Update 형식)
    public void remove(Integer id) {
        boardMapper.remove(id);
    }
}
   