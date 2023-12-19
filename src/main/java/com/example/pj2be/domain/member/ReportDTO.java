package com.example.pj2be.domain.member;

import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDTO {
    private int id;
    private int board_id;
    private int is_reported;
    private String reporter_id;
    private String reported_id;
    private LocalDateTime report_date;
    private boolean is_resolved;
    private String report_reason;
    private int countReported;
    private String categoryName;
    private String title;
    private int total_records;
    private String role_name;
    private String nickname;
    private int row_num;
    private String category_name;
    public String getAgo() {
        return ChangeTimeStamp.getAgo(report_date);
    }

}
