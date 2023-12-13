package com.example.pj2be.domain.vote;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteCountDTO {

    private Integer id;
    private Integer vote_board_id;
    private String vote_member_id;
    private Integer voted_all;
    private Integer voted_a;
    private Integer voted_b;
    private LocalDateTime created_at;
}
