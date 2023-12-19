package com.example.pj2be.controller.membercontroller;


import com.example.pj2be.domain.member.ReportDTO;
import com.example.pj2be.service.memberservice.MemberReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @GetMapping("/list")
    public Map<String, Object> getReporterList(@RequestParam(value = "pg", defaultValue = "1") Integer page,
                                                @RequestParam("rc") String reportCategory){
        Map<String, Object> reportList = memberReportService.getReporterList(page, reportCategory);

        return reportList;
    }
    @GetMapping("/reportedList")
    public Map<String, Object> getReporterList(@RequestParam("reported_id") String reported_id){
        Map<String, Object> reportedList = memberReportService.getAllReportedList(reported_id);

        return reportedList;
    }
    @PatchMapping("/resolved")
    public ResponseEntity updateResolved(@RequestBody ReportDTO reportDTO){
        String reportedId = reportDTO.getReported_id();
        if(memberReportService.updateResolved(reportedId)){
            System.out.println("resolved 성공!");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
    @DeleteMapping("/reject")
    public ResponseEntity rejectReport(@RequestParam("reported_id") String reportedId ){
       if(memberReportService.rejectReport(reportedId)){
           return ResponseEntity.ok().build();
       }
       return ResponseEntity.internalServerError().build();
    }
}
