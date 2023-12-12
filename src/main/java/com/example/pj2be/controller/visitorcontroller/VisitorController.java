package com.example.pj2be.controller.visitorcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VisitorController {

    @GetMapping("visitor")
    public Integer boardDataList() {

        System.out.println("방문자 컨트롤러");

        return null;

    }
}
