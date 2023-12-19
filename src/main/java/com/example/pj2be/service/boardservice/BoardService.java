package com.example.pj2be.service.boardservice;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.category.CategoryDTO;
import com.example.pj2be.mapper.boardmapper.BoardMapper;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    // 게시글 작성
    public void save(BoardDTO board, MultipartFile[] files, String[] uuSrc, CategoryDTO category) throws Exception {
        boardMapper.insert(board, category);

        if (files != null) {
                for (MultipartFile file : files) {
                    fileService.s3Upload(file, board.getId());
                }
        }

        /* 본문 ck에디터영역에 실제로 저장된 이미지 소스코드와 게시물ID 보내기 */
        if (uuSrc != null) {
            String ck_category = "C002";
            fileService.ckS3Update(uuSrc, board.getId(), ck_category);

            // 임시로 저장된 이미지 삭제 ( board_id = 0 인 것 )
            fileService.ckS3DeleteTempImg();
        }
    }

    // 게시글 리스트, 페이징
    public Map<String, Object> list(Integer page, String keyword, String type, Integer slice, String category) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();

        System.out.println("page: " + page + " / keyword: " + keyword + " / type: " + type + " / category: " + category);

        // 페이징 필요한 것들
        // 전체페이지, 보여줄페이지 수, 왼쪽끝페이지, 오른쪽끝페이지, 담페이지, 이전페이지,
        int countAll = 0;


        if(category.equals("all")) {
            // 통합검색
            countAll = boardMapper.selectMainAllpage("%" + keyword + "%", category);
            System.out.println("타입 없음 총 게시글 실행: " + countAll);
        } else {
            // 개별 게시판 검색
            countAll = boardMapper.selectAllpage("%" + keyword + "%", type, category);
            System.out.println("타입 있음 총 게시글 실행: " + countAll);
        }

        int lastPageNumber = (countAll - 1) / slice + 1;
        int startPageNumber = (page - 1) / 5 * 5 + 1;
        int endPageNumber = (startPageNumber + (5 - 1));
        endPageNumber = Math.min(endPageNumber, lastPageNumber);
        int prevPageNumber = startPageNumber - 5;
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

        if(category.equals("all")){
            // 통합 검색
            map.put("boardList", boardMapper.mainSelectAll(from, slice, "%" + keyword + "%", category));
            map.put("listCount", countAll);
            map.put("pageInfo", pageInfo);
            map.put("boardInfo", boardMapper.boardCategory(category));
            System.out.println("타입 없음 게시글 보기 실행: " + map.get("boardList"));
        } else {
            // 개별 게시판
            map.put("boardList", boardMapper.selectAll(from, slice, "%" + keyword + "%", type, category));
            map.put("pageInfo", pageInfo);
            map.put("boardInfo", boardMapper.boardCategory(category));
            System.out.println("타입 있음 게시글 보기 실행: " + map.get("boardList"));
        }

        System.out.println("pageInfo = " + pageInfo);

        

        return map;
    }

    // 게시글 보기
    @Transactional(readOnly = true)
    public BoardDTO get(Integer id) {
        BoardDTO board = boardMapper.selectById(id);

        System.out.println("@@@@@@@@@@@@@@@@@@ " + id + "번 게시글 보기.");

        return board;
    }

    // 게시글 수정
    public void update(BoardDTO board, MultipartFile[] files) throws Exception {
        System.out.println(board.getId() + "번 게시물 수정 시작 (서비스)");

        if (board.getUuSrc() != null) {
            /* BoardEditDTO의 List<String>타입의 uuSrc를 배열에 담는다. */
            String[] uuSrc = board.getUuSrc().toArray(new String[0]);

            if (uuSrc != null) {
                /* 본문 ck에디터영역에 실제로 저장된 이미지 소스코드와 게시물ID 보내기, 업로드 이미지에 게시물id 부여 */
                String ck_category = "C002";
                fileService.ckS3Update(uuSrc, board.getId(), ck_category);

                // 임시로 저장된 이미지 삭제 ( board_id = 0 인 것 )
                fileService.ckS3DeleteTempImg();
            }
        }

        if (files != null) {
            for (MultipartFile file : files) {
                fileService.s3Upload(file, board.getId());
            }
        }

        boardMapper.update(board);

    }

    // 게시글 삭제 (Update 형식)
    public void remove(Integer id) {
        boardMapper.remove(id);
        System.out.println("@@@@@@@@@@@@@@@@@@ " + id + "번 게시글 삭제.");
    }

    // 게시글 조회수 증가
    public void increaseViewCount(Integer id) {
        boardMapper.increaseViewCount(id);
        System.out.println("@@@@@@@@@@@@@@@@@@ " + id + "번 게시글 조회수 증가.");
    }


    public Map<String, Object> randomGet() {

        Random random = new Random();

        List<Map<String, Object>> list = boardMapper.randomGet();


        int randomIndex = random.nextInt(list.size());

        return list.get(randomIndex);

    }


}
   