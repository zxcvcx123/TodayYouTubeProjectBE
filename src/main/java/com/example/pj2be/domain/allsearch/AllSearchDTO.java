package com.example.pj2be.domain.allsearch;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AllSearchDTO {
    private String resultCategory;
    private String categoryCount;
    private String title;
    private String content;
    private LocalDateTime created_at;
}
