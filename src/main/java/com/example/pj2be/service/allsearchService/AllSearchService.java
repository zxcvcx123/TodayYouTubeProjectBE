package com.example.pj2be.service.allsearchService;

import com.example.pj2be.mapper.AllSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class AllSearchService {

        private final AllSearchMapper mapper;

    public Map<String, Object> allSearch(String allKeyword) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> categoryMap = new HashMap<>();

        categoryMap.put("searchResultMenuList", mapper.selectByallKeyword("%" + allKeyword + "%"));

        // 카테고리별
        categoryMap.put("sportsResult", mapper.selectCategoryByAllKeyword("%" + allKeyword + "%", "스포츠"));
        map.put("searchResultMenu", categoryMap);

        return map;
    }
}
