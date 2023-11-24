package com.example.pj2be.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {
    private int id;
    private String title;
    private String content;
    private String link;
    private String categoryCode;
    private String memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
