package com.example.pj2be.controller.allsearchcontroller;

import com.example.pj2be.service.allsearchService.AllSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class AllSearchController {

        private final AllSearchService service;

    @GetMapping
    public Map<String, Object> allSearch(@RequestParam (value = "allSearch", required = false) String allKeyword) {

        System.out.println("allKeyword = " + allKeyword);

        return service.allSearch(allKeyword);

    }

}
