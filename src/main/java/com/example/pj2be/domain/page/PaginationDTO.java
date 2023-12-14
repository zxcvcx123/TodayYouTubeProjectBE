package com.example.pj2be.domain.page;

import lombok.Data;

@Data
public class PaginationDTO {
    private Integer allPage = 0; // 전체페이지 (mapper로 COUNT)
    private Integer initialPage = 1; // 1페이지
    private Integer currentPageNumber = 0; // 현재페이지 (param으로 받아서)
    private Integer slice = 5; // Limit from, slice
    private Integer limitList = 10;
    // 연산되는 값은 무조건 get으로 설정해야 한다.
    public Integer getFrom() {
        return (currentPageNumber - 1) * limitList;
    }
    public Integer getLastPageNumber() {
        return ( allPage - 1) / limitList + 1;
    }
    public Integer getStartPageNumber() {
        return (currentPageNumber - 1) / limitList * limitList + 1;
    }
    public Integer getEndPageNumber() {
        return Math.min(getStartPageNumber() + (limitList - 1), getLastPageNumber());
    }
    public Integer getPrevPageNumber() {
       return (getStartPageNumber() - slice) ;
    }
    public Integer getNextPageNumber() {
        return (getEndPageNumber() + 1 );
    }

}
