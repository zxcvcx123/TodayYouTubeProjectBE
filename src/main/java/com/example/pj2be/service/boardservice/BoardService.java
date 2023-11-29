package com.example.pj2be.service.boardservice;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.mapper.boardmapper.BoardMapper;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    // 게시글 작성
    // TODO : 타이틀, 본문 isBlank면 작성 되어서는 안됨
    public void save(BoardDTO board, MultipartFile[] files, String[] uuSrc) throws Exception {
        boardMapper.insert(board);

        if(files != null) {
            for (MultipartFile file : files) {
                fileService.s3Upload(file, board.getId());
            }
        }

        fileService.ckS3Update(uuSrc, board.getId());
    }

    // 게시글 리스트, 페이징
    public Map<String, Object> list(Integer page, String keyword, String category) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();

        // 페이징 필요한 것들
        // 전체페이지, 보여줄페이지 수, 왼쪽끝페이지, 오른쪽끝페이지, 담페이지, 이전페이지,
        int countAll;
        countAll = boardMapper.selectAllpage("%" + keyword + "%", category);
        int slice = 5;
        int lastPageNumber = (countAll - 1) / slice + 1;
        int startPageNumber = (page - 1) / slice * slice + 1;
        int endPageNumber = (startPageNumber + (slice - 1));
        endPageNumber = Math.min(endPageNumber, lastPageNumber);
        int prevPageNumber = startPageNumber - slice;
        int nextPageNumber = endPageNumber + 1;
        int initialPage = 1;

        // 넘겨줄 것들 put

        pageInfo.put("currentPageNumber", page);
        pageInfo.put("startPageNumber", startPageNumber);
        pageInfo.put("endPageNumber", endPageNumber);

        if (prevPageNumber > 0) {
            pageInfo.put("prevPageNumber", prevPageNumber);
            pageInfo.put("initialPage", initialPage);
        }
        if (nextPageNumber <= lastPageNumber) {
            pageInfo.put("nextPageNumber", nextPageNumber);
            pageInfo.put("lastPageNumber", lastPageNumber);
        }

        int from = (page - 1) * slice;


        map.put("boardList", boardMapper.selectAll(from, slice, "%" + keyword + "%", category));
        map.put("pageInfo", pageInfo);


        return map;
    }

    // 게시글 보기
    @Transactional(readOnly = true)
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

    // 게시글 조회수 증가
    @Transactional
    public void increaseViewCount(Integer id) {
        boardMapper.increaseViewCount(id);
    }


}
   