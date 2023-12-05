package com.example.pj2be.controller.boardcontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.board.BoardEditDTO;
import com.example.pj2be.service.boardservice.BoardService;
import com.example.pj2be.service.fileservice.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
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
    // @Valid 어노테이션과 BindingResult 객체를 통해 유효성 검증
    @PostMapping("add")
    public ResponseEntity add(@Valid BoardDTO board,
                                      BindingResult bindingResult,
                                      @RequestParam(value = "uploadFiles[]", required = false) MultipartFile[] files,
                                      @RequestParam(value = "uuSrc[]", required = false) String[] uuSrc) throws Exception {
        System.out.println("##################board = " + board);

        // BoardDTO 유효성 검증 실패시 에러(400) 반환
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println("@@@@@@@@@@@@@@@@@@" + board.getBoard_member_id() + "님이 게시글 작성함.");
        boardService.save(board, files, uuSrc);

        return ResponseEntity.ok().body("BoardDto 객체 _ title, content 검증 완료");
    }

    // 게시글 목록
    // 페이징 수정했음, 검색카테고리 수정
    @GetMapping("list")
    public Map<String, Object> list(
            @RequestParam(value = "p", defaultValue = "1") Integer page,
            @RequestParam(value = "c", defaultValue = "all") String category,
            @RequestParam(value = "k", defaultValue = "") String keyword,
            @RequestParam(value = "s", defaultValue = "10") Integer slice) {


        return boardService.list(page, keyword, category, slice);
    }

    // 게시글 보기
    @Transactional
    @GetMapping("id/{id}")
    public BoardDTO view(@PathVariable Integer id) {
        // 게시글 조회수 증가
        boardService.increaseViewCount(id);

        return boardService.get(id);
    }

    // 게시글 수정
    //@PreAuthorize("isAuthenticated() and ((#board.getBoard_member_id() == #login_member_id) and hasRole('ROLE_GENERAL_MEMBER'))")
    @PutMapping("edit")
    public ResponseEntity edit(BoardDTO board, String login_member_id,
                                       @RequestParam(value = "uploadFiles[]", required = false) MultipartFile[] files) throws Exception {
        System.out.println(board.getId() + "번 게시물 수정 시작 (컨트롤러)");

        System.out.println("게시글을 작성했던 사용자 아이디 = " + board.getBoard_member_id());
        System.out.println("로그인 중인 사용자 아이디 = " + login_member_id);

        // 게시글 작성자 id와 로그인 사용자 id를 비교하여 유효성 검증
        if (board.getBoard_member_id().equals(login_member_id)) {
            boardService.update(board, files);
            return ResponseEntity.ok().body("게시글 수정 완료");
        } else if (login_member_id.isBlank()) {
            System.out.println("@@@@@@@@@@@@@@@@@@로그인 중인 사용자 아이디 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            System.out.println("@@@@@@@@@@@@@@@@@@작성자와 로그인 사용자 아이디 다름");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
