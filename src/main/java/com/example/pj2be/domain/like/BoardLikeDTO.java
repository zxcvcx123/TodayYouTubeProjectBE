package com.example.pj2be.domain.like;

import lombok.Data;

@Data
public class BoardLikeDTO {
    private Integer id;
    private Integer board_id;
    private String member_id;
}
