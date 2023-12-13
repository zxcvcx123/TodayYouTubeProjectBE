package com.example.pj2be.domain.page;

import lombok.Data;

@Data
public class PageDTO {
    
    private Integer initialPage = 1; // 맨 첫페이지
    private Integer page = 1; // 현재 페이지
    private Integer limitList = 5; // Limit 뒤에 숫자 (글을 몇 개 보여줄건지)
    private Integer slicePage = 5; // 1~ 몇 페이지 씩 보여줄건지;
    private Integer totalList = 0; // 전체 페이지 수

    // Limit 앞에 숫자
    public Integer getLimitNowPage() {
        return (page - 1) * limitList;
    }

    // 총 페이지
    public Integer getTotalPage() {
        return (totalList / limitList) + (totalList % limitList > 0 ? 1 : 0);
    }

    // 페이지 시작점;
    public Integer getStartPage() {
        return (page - 1) / slicePage * slicePage + 1;
    }

    // 페이지 마지막 번호
    public Integer getLastPage() {
        return ((totalList - 1) / slicePage) + 1;
    }

    // 페이지 끝점
    public Integer getEndPage() {
        int endPage = getStartPage() + (slicePage - 1);
        return Math.min(endPage, getLastPage());
    }

    // 다음 페이지
    public Integer getNextPageNumber(){
        return getEndPage() + 1;
    }

    // 이전 페이지
    public Integer getPrevPageNumber(){
        return getStartPage() - slicePage;
    }

    // 마지막 페이지
    public Integer getLastPageNumber(){
        return (totalList -1) / slicePage + 1;
    }

}
