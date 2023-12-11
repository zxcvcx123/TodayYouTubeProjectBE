package com.example.pj2be.domain.admin;

import lombok.Data;

@Data
public class BoardDataDTO {

    private String name_eng;        // 게시판 카테고리
    private Integer count_category_board;   // 카테고리 별 게시글 작성 수
    private Integer count_category_view;    // 카테고리 별 게시글 조회수 합
    private Integer count_category_members_man;      // 카테고리 별 글작성 성비 (남)
    private Integer count_category_members_woman;    // 카테고리 별 글작성 성비 (여)
}
