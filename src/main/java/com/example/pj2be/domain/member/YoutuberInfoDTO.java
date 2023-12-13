package com.example.pj2be.domain.member;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YoutuberInfoDTO {
    private Integer id;
    private String member_id;
    private String title;
    private String country;
    private LocalDateTime publishedAt;
    private String customUrl;
    private String description;
    private String thumbnails;
    private String subscriberCount;
    private String videoCount;
    private String viewCount;

    public String getAgo() {
        return ChangeTimeStamp.getAgo(publishedAt);
    }


}
