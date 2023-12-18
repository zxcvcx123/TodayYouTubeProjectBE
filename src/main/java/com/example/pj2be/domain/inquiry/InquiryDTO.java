package com.example.pj2be.domain.inquiry;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

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
    private String answerContent;
    private String login_member_id;
    private String role_name;
    private String[] uuSrc;
    private String ck_category;
    private String category_code;


    public String getAgo() {
        return ChangeTimeStamp.getAgo(created_at);
    }
}
