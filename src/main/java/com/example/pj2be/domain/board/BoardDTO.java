package com.example.pj2be.domain.board;

import com.example.pj2be.utill.ChangeTimeStamp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDTO {

    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String link;
    private String board_category_code;
    private String board_member_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean is_show;
    private LocalDateTime deleted_at;
    private Integer countlike;
    private Integer count_comment;
    private Integer views;
    private List<String> uuSrc;
    private String categoryName;
    private String login_member_id;
    public String getAgo() {
        return ChangeTimeStamp.getAgo(updated_at);
    }


}
