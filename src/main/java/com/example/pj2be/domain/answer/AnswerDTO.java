package com.example.pj2be.domain.answer;

import lombok.Data;

@Data
public class AnswerDTO {
    private Integer id;
    private Integer answer_board_id;
    private String title;
    private String content;
    private String role_name;
}
