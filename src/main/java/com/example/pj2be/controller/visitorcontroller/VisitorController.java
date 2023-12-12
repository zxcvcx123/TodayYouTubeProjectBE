package com.example.pj2be.controller.visitorcontroller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VisitorController {

    @GetMapping("visitor")
    public Integer boardDataList(HttpServletRequest request,
                                 @RequestParam(required = false) String member_id) {
        // IP 주소 얻기
        String clientIp = request.getRemoteAddr();

        // 정보 출력 (디버깅)
        System.out.println("Client IP Address: {}" + clientIp);
        System.out.println("member id: " + member_id);
        System.out.println("방문자 컨트롤러");

        return null;

    }
}
