package com.example.pj2be.domain.visitor;

import lombok.Data;

import java.util.List;

@Data
public class VisitorDTO {
    private Integer visitorCountAll;
    private Integer visitorCountToday;
    private List<VisitorMonthlyDTO> visitorCountMonthlyLastYear;
}
