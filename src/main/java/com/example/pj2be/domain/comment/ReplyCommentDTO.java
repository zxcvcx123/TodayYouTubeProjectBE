package com.example.pj2be.domain.comment;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyCommentDTO {

    private Integer id;
    private Integer comment_id;
    private String member_id;
    private String reply_comment;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String nickname;
    private String code;

    public String getAgo() {
        return ChangeTimeStamp.getAgo(created_at);
    }

}
