package com.example.pj2be.service.adminservice;

import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.page.PaginationDTO;
import com.example.pj2be.mapper.adminmapper.AdminMapper;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final MemberMapper memberMapper;

    public Map<String, Object> getBoardData() {
        Map<String, Object> map = new HashMap<>();

        map.put("boardDataList", adminMapper.getBoardData());

        System.out.println("어드민 서비스 보드데이터");

        return map;
    }

    public Map<String, Object> getUserData() {
        Map<String, Object> map = new HashMap<>();

        map.put("userWriteRankDataList", adminMapper.getUserWriteRankData());
        map.put("userLikeRankDataList", adminMapper.getUserLikeRankData());
        map.put("userCommentRankDataList", adminMapper.getUserCommentRankData());

        System.out.println("어드민 서비스 유저데이터");

        return map;
    }

    // 정지회원 관리 페이지
    public Map<String, Object> getSuspensionList(Integer page, PaginationDTO paginationDTO) {
        Map<String, Object> map = new HashMap<>();


        // 정지중인 회원들 리스트 페이징
        paginationDTO.setAllPage(adminMapper.selectAllMember());
        paginationDTO.setCurrentPageNumber(page);
        paginationDTO.setLimitList(5);

        map.put("pageInfo", paginationDTO);

        int from = paginationDTO.getFrom();
        int limit = paginationDTO.getLimitList();

        // 정지중인 회원들 리스트 불러오기(페이징)
        map.put("suspensionList", adminMapper.selectSuspensioningMember(from, limit));
        map.put("releaseList", adminMapper.selectReleaseMember());

        System.out.println("###############" + paginationDTO );

        return map;

    }

    // 정지된 회원 정지해제
    public void updateSuspension(SuspensionDTO dto) {

        System.out.println(dto);

        // 정지해제된 멤버의 role을 아이언 회원으로
        memberMapper.updateByReleaseId(dto.getMember_id());
        System.out.println("eeeeeeeeee");

        // 정지해제 Suspension 테이블에서 삭제
        adminMapper.deleteSuspension(dto.getId());

    }

    public SuspensionDTO getSuspensionMember(String memberId) {
        return adminMapper.selectSuspensionMember(memberId);
    }
}
