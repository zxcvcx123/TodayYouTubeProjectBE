package com.example.pj2be.domain.visitor;

import lombok.Data;

@Data
public class VisitorMonthlyDTO {
    private String year_month;
    private Integer visitor_count;
}
