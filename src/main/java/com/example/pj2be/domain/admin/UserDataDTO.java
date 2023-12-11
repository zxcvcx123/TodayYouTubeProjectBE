package com.example.pj2be.domain.admin;

import lombok.Data;

@Data
public class UserDataDTO {
    private String member_id;
    private Integer write_count;
    private Integer write_rank;
    private Integer like_count;
    private Integer like_rank;
    private Integer comment_count;
    private Integer comment_rank;
}
