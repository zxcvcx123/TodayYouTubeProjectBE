package com.example.pj2be.domain.inquiry;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InquiryDTO {
    private Integer id;
    private String inquiry_category;
    private String title;
    private String content;
    private String inquiry_member_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String answer_status;
}
