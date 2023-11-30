package com.example.pj2be.controller.boardcontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.board.BoardEditDTO;
import com.example.pj2be.service.boardservice.BoardService;
import com.example.pj2be.service.fileservice.FileService;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    // 게시글 작성
    // ckeditor 영역에 업로드된 이미지의 소스코드를 배열 형태로 받아옴.
    @PostMapping("add")
    public void add(BoardDTO board,
                    @RequestParam(value = "uploadFiles[]", required = false) MultipartFile[] files,
                    @RequestParam(value = "uuSrc[]", required = false) String[] uuSrc) throws Exception {

        boardService.save(board, files, uuSrc);
    }

    // 게시글 목록
    // 페이징 수정했음, 검색카테고리 수정
    @GetMapping("list")
    public Map<String, Object> list(
            @RequestParam(value = "p", defaultValue = "1") Integer page,
            @RequestParam(value = "c", defaultValue = "all") String category,
            @RequestParam(value = "k", defaultValue = "") String keyword,
            @RequestParam(value = "s", defaultValue = "10") Integer slice) {



        return boardService.list(page,keyword,category,slice);
    }

    // 게시글 보기
    @Transactional
    @GetMapping("id/{id}")
    public BoardDTO view(@PathVariable Integer id) {
        // 게시글 조회수 증가
        boardService.increaseViewCount(id);

        return boardService.get(id);
    }

    // 게시글 수정e
    @PutMapping("edit")
    public void edit(BoardDTO board,
                    @RequestParam(value = "uploadFiles[]", required = false) MultipartFile[] files) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@@게시판 테스트: " + board);
        System.out.println(board.getId() + "번 게시물 수정 시작 (컨트롤러)");
        boardService.update(board, files);
    }

    // 게시글 삭제 (Update 형식)
    @PutMapping("remove/{id}")
    public void remove(@PathVariable Integer id) {
        boardService.remove(id);
    }

    // 테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트
    @PostMapping("test")
    public void test(@RequestParam(value = "file") MultipartFile file) {
        System.out.println("file = " + file.getOriginalFilename());
        System.out.println("file.getSize() = " + file.getSize());
        System.out.println("file.getContentType() = " + file.getContentType());

    }
}
