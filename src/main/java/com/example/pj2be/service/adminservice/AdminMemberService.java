package com.example.pj2be.service.adminservice;

import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.page.PaginationDTO;
import com.example.pj2be.mapper.adminmapper.AdminMemberMapper;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import com.example.pj2be.utill.ChangeTimeStamp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberMapper mapper;
    private final MemberMapper memberMapper;

    public Map<String, Object> memberlist(Integer page, String mid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();

        // 페이징 필요한 것들
        // 전체페이지, 보여줄페이지 수, 왼쪽끝페이지, 오른쪽끝페이지, 담페이지, 이전페이지,
        int countAll;

        countAll = mapper.selectAll("%" + mid + "%");

        int lastPageNumber = (countAll - 1) / 10 + 1;
        int startPageNumber = (page - 1) / 10 * 10 + 1;
        int endPageNumber = (startPageNumber + (10 - 1));
        endPageNumber = Math.min(endPageNumber, lastPageNumber);
        int prevPageNumber = startPageNumber - 10;
        int nextPageNumber = endPageNumber + 1;
        int initialPage = 1;

        // 넘겨줄 것들 put

        pageInfo.put("currentPageNumber", page);
        pageInfo.put("startPageNumber", startPageNumber);
        pageInfo.put("endPageNumber", endPageNumber);

        if (prevPageNumber > 0) {
            pageInfo.put("prevPageNumber", prevPageNumber);
            pageInfo.put("initialPage", initialPage);
        }
        if (nextPageNumber <= lastPageNumber) {
            pageInfo.put("nextPageNumber", nextPageNumber);
            pageInfo.put("lastPageNumber", lastPageNumber);
        }

        int from = (page - 1) * 10;
        map.put("pageInfo", pageInfo);

        map.put("memberList", mapper.selectAllMember(from, "%" + mid + "%"));
        return map;
    }

    public Map<String, Object> memberInfo(String memberId, Integer page, PaginationDTO paginationDTO) {
        Map<String, Object> map = new HashMap<>();

        // 활동한 게시판목록
        map.put("activeBoard", mapper.selectActiveBoard(memberId));

        // 멤버정보 가져오기
        map.put("memberList", mapper.selectByMemberId(memberId));




        // 게시물 페이징
        paginationDTO.setAllPage(mapper.selectAllMemberBoard(memberId));
        paginationDTO.setCurrentPageNumber(page);
        paginationDTO.setLimitList(10);

        map.put("pageInfo", paginationDTO);

        // 작성한 게시글 가져오기
        map.put("memberInfoBoardList", mapper.selectBoardList(memberId, paginationDTO));


        // 댓글 페이징
        PaginationDTO paginationDTO2 = new PaginationDTO();
        paginationDTO2.setCurrentPageNumber(page);
        paginationDTO2.setAllPage(mapper.selectAllMemberComment(memberId));
        paginationDTO2.setLimitList(20);

        map.put("pageInfo2", paginationDTO2);

        // 작성한 댓글 가져오기
        map.put("memberInfoCommentList", mapper.selectCommentList(memberId, paginationDTO2));

        return map;
    }

    // 회원 정지 진행중
    public ResponseEntity memberSuspension(SuspensionDTO suspensionDTO) {
//        LocalDateTime endDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        int suspensionDay = 4;

        if (suspensionDTO.getPeriod() == 7) {
            suspensionDay = 7;
        } else if (suspensionDTO.getPeriod() == 30) {
            suspensionDay = 30;
        } else if (suspensionDTO.getPeriod() == 999) {
            suspensionDay = 999;
        }

        // 회원 이미 정지 상태라면(role변경쿼리 return이 0이라면) --> badRequest
        if (memberMapper.changeRoleToSuspension(suspensionDTO.getMember_id()) == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {

            // 회원 테이블의 role을 11번 '정지회원'으로 변경
            memberMapper.changeRoleToSuspension(suspensionDTO.getMember_id());

            // 정지 테이블에 추가
            mapper.insertSuspensionStart(suspensionDTO, suspensionDay);
            System.out.println(suspensionDTO.getMember_id() + "회원이 " + suspensionDTO.getPeriod() + "일간 정지되었습니다. => 사유(" + suspensionDTO.getReason() +")");

            return ResponseEntity.ok().build();
        }
    }

}
