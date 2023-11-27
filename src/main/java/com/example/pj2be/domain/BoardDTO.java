package com.example.pj2be.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private Integer id; // pk
    private String title; // 제목
    private String content; // 내용
    private String link; // 링크
    private LocalDateTime created_at; // 작성날짜
    private LocalDateTime updated_at; // 수정날짜

    // FK
    private String category; // category_code / 카테고리
    private String writer; // member_member_id / 작성자 아이디

}
