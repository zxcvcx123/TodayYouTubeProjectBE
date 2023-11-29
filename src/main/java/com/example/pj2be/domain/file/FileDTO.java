package com.example.pj2be.domain.file;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileDTO {

    private Integer id;
    private Integer board_id;
    private String filename;
    private String fileurl;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
