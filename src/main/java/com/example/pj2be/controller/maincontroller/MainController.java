package com.example.pj2be.controller.maincontroller;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.service.mainservice.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final MainService service;

    @GetMapping
    public Map<String, Object> get(@RequestParam String c) {

        return service.select(c);

    }
}
