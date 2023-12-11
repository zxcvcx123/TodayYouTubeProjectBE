package com.example.pj2be.domain.vote;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteDTO {

    private Integer id;
    private String vote_member_id;
    private String title;
    private String link_a;
    private String link_b;
    private String content;
    private String name_eng;
    private LocalDateTime created_at;
}
