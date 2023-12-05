package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberInfoMapper;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoMapper memberInfoMapper;
    private final MemberMapper memberMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public Map<String, Object> getMyBoardList(String member_id, String categoryOrdedBy, String categoryTopics, Integer page) {

        Map<String, Object> myBoardListMap = new HashMap<>();
        Map<String, Object> pagingInformation = new HashMap<>();
        int countAll = memberInfoMapper.countAll(member_id, categoryTopics); // 기준 별 글 수

        int lastPageNumber = (countAll-1)/ 10 +1; // 마지막 페이지 번호 (예: 14 -> 2)

        int startPageNumber = (page -1)/10*10 +1;   // 페이지 그룹의 시작 번호
        int endPageNumber = startPageNumber + 9;    // 페이지 그룹의 마지막 번호
        endPageNumber= Math.min(endPageNumber, lastPageNumber); // 가장 끝 페이지 그룹에서 마지막 번호를 나타내기 위함
        pagingInformation.put("currentPageNumber", page);   // 현재 위치한 페이지
        pagingInformation.put("startPageNumber", startPageNumber);
        pagingInformation.put("endPageNumber", endPageNumber);
        pagingInformation.put("lastPageNumber", lastPageNumber);
        int prevPageNumber = startPageNumber -10;   // 페이지 그룹의 이전
        int nextPageNumber = endPageNumber +1;  // 페이지 그룹의 이후
        if(prevPageNumber > 0){ // 현재 페이지 그룹이 1~10인 경우 0이 되는 것을 방지
            pagingInformation.put("prevPageNumber", startPageNumber -10);
        }
        if(nextPageNumber< lastPageNumber){ // 가장 마지막 페이지보다 페이지 그룹 끝이 작을 때만 표시
            pagingInformation.put("nextPageNumber", nextPageNumber);
        }
        int from = (page -1) * 10;
        myBoardListMap.put("myBoardList", memberInfoMapper.getMyBoardList(member_id, categoryOrdedBy, categoryTopics, from));
        myBoardListMap.put("pagingInformation", pagingInformation);
        return myBoardListMap;
    }

    public boolean validateMemberPassword(String memberId, String password) throws UsernameNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication validateAuthentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return validateAuthentication.isAuthenticated();
    }
}
