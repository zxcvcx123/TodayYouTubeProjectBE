package com.example.pj2be.controller.membercontroller;

import com.example.pj2be.domain.member.ReportDTO;
import com.example.pj2be.service.memberservice.MemberReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/report")
@RequiredArgsConstructor
public class MemberReportController {
    private final MemberReportService memberReportService;

    @PostMapping
    public ResponseEntity reportMember(@RequestBody ReportDTO reportDTO){
        if(memberReportService.memberReport(reportDTO)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping
    public boolean isMemberReported(@RequestParam("reporter_id") String reporter_id,
                                    @RequestParam("reported_id") String reported_id,
                                    @RequestParam("board_id") Integer board_id){
        return memberReportService.isMemberReported(reporter_id, reported_id, board_id);
    }
}
