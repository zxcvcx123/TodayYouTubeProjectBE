package com.example.pj2be.domain.minihomepy;

import lombok.Data;

@Data
public class MiniHomepyDTO {
    private int homepy_id;
    private String member_id;
    private String login_member_id;
    private String title;
    private String introduce;
    private Integer today_visitors;
    private Integer total_visitors;
    private String bgm_link;
}
