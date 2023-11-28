package com.example.pj2be.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private int id;
    private String title;
    private String content;
    private String link;
    private String board_category_code;
    private String board_member_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean is_show;
    private LocalDateTime deleted_at;
    private Integer countlike;


}
