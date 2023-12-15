package com.example.pj2be.domain.minihomepy;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MiniHomepyCommentDTO {
    private Integer id;
    private int homepy_id;
    private String member_id;
    private String nickname;
    private String role_name;
    private String comment;
    private String image_url;
    private LocalDateTime inserted_at;

    public String getAgo() {
        return ChangeTimeStamp.getAgo(inserted_at);
    }

}
