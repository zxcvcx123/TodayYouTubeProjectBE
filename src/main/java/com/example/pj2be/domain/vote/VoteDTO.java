package com.example.pj2be.domain.vote;

import com.example.pj2be.utill.ChangeTimeStamp;
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
    private String nickname;
    private String rolename;

    // ---------- 시간 포맷 변경 ----------
    public String getAgo() {
        return ChangeTimeStamp.getAgo(created_at);
    }

}
