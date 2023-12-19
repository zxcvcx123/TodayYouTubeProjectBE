package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.ReportDTO;
import com.example.pj2be.mapper.membermapper.MemberReportMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberReportService {
    private final MemberReportMapper memberReportMapper;


    public boolean memberReport(ReportDTO reportDTO) {
        String reporterId = reportDTO.getReporter_id();
        String reportedId = reportDTO.getReported_id();
        String reportReason = reportDTO.getReport_reason();
        int boardId = reportDTO.getBoard_id();
        return memberReportMapper.reportMemberByMemberId(reporterId, reportedId, reportReason, boardId);
    }

    public boolean isMemberReported(String reporterId, String reportedId, Integer boardId) {
        return memberReportMapper.isMemberReported(reporterId, reportedId, boardId) == 0;
    }

    public Map<String, Object> getReporterList(Integer page, String reportCategory) {
        Map<String, Object> reporterList = new HashMap<>();
        Map<String, Object> pagingInformation = new HashMap<>();
        int countAll = memberReportMapper.countAll(reportCategory);

        int lastPageNumber = (countAll-1)/ 10 +1;

        int startPageNumber = (page -1)/10*10 +1;
        int endPageNumber = startPageNumber + 9;
        endPageNumber= Math.min(endPageNumber, lastPageNumber);
        pagingInformation.put("currentPageNumber", page);
        pagingInformation.put("startPageNumber", startPageNumber);
        pagingInformation.put("endPageNumber", endPageNumber);
        pagingInformation.put("lastPageNumber", lastPageNumber);
        int prevPageNumber = startPageNumber -10;
        int nextPageNumber = endPageNumber +1;
        if(prevPageNumber > 0){
            pagingInformation.put("prevPageNumber", startPageNumber -10);
        }
        if(nextPageNumber< lastPageNumber){
            pagingInformation.put("nextPageNumber", nextPageNumber);
        }
        int from = (page -1) * 10;
        reporterList.put("reporterList", memberReportMapper.getReportList(reportCategory, from));
        reporterList.put("pagingInformation", pagingInformation);
        return reporterList;
    }

    public Map<String, Object> getAllReportedList(String reported_id) {
        Map<String, Object> allReportedList = new HashMap<>();
        allReportedList.put("allReportedList",memberReportMapper.getAllReportedList(reported_id));
    return allReportedList;
    }

    public boolean updateResolved(String reportedId) {
        boolean bool = memberReportMapper.updateResolved(reportedId) && memberReportMapper.changeIsShow(reportedId);

        return bool;
    }

    public boolean rejectReport(String reportedId) {
   return memberReportMapper.rejectReport(reportedId);
    }
}
