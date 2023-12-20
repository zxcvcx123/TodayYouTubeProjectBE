package com.example.pj2be.domain.board;

import com.example.pj2be.utill.ChangeTimeStamp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class BoardDTO {


    // ---------- board table ----------
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String link;
    private String board_category_code;
    private String board_member_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean is_show;
    private LocalDateTime deleted_at;
    private Integer views;

    // ---------- 좋아요, 댓글 ----------
    private Integer countlike;
    private Integer count_comment;

    // ---------- CKeditor uuSrc ----------
    private List<String> uuSrc;

    // ---------- 회원 닉네임, 등급 ----------
    private String nickname;
    private String role_name;

    // ---------- 카테고리명 ----------
    private String categoryName;
    private String name_eng;

    // ---------- 로그인 사용자 유효성 검증 ----------
    private String login_member_id;

    // ---------- 게시물 번호 저장 ----------
    private Integer rownum;

    // ---------- 시간 포맷 변경 ----------
    private Boolean isYouTubeLink;

    // ---------- 시간 포맷 변경 ----------
    public String getAgo() {
        return ChangeTimeStamp.getAgo(updated_at);
    }

    // 메인 key 겹쳐서 uuid로 뿌려주기
    public String getUuid(){

        String uuid = String.valueOf(UUID.randomUUID());
        return uuid;
    }


}
