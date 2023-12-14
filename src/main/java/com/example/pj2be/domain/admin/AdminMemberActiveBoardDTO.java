package com.example.pj2be.domain.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminMemberActiveBoardDTO {
    private String name;
    private Integer countactive;
    private Integer id;
    private Integer board_id;
    private String title;
    private String comment;
    private LocalDateTime created_at;
    private Integer countcommentlike;
}
