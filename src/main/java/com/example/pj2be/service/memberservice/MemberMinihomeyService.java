package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyDTO;
import com.example.pj2be.mapper.membermapper.MiniHomepyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberMinihomeyService {

    private final MiniHomepyMapper miniHomepyMapper;
    private final MemberLoginService memberLoginService;
    public MemberDTO getMemberMinihomepyInfo(String member_id) {
              // member 정보 가져오기
        MemberDTO memberInfo = memberLoginService.getLoginInfo(member_id);
        memberInfo.setEmail("");
        memberInfo.setPhone_number("");
        memberInfo.setId(null);

        return memberInfo;
    }

    public MiniHomepyDTO getMinihomepyInfo(String memberId) {
        return miniHomepyMapper.getInfo(memberId);
    }

    public Boolean updateIntroduce(String member_id, String introduce) {
        boolean bool = miniHomepyMapper.updateIntroduceByMemberId(member_id, introduce);
        return bool;
    }

    public Boolean addMiniHomepyVisiterViewByMemberId(MiniHomepyDTO miniHomepyDTO) {
        String member_id = miniHomepyDTO.getMember_id();
        String login_member_id = miniHomepyDTO.getLogin_member_id();
        if(!miniHomepyMapper.checkLoginMemberIdExistsInCurrentDate(member_id, login_member_id)){
            return miniHomepyMapper.addHomepyVisiterViewByLoginMemberId(member_id, login_member_id);
        }
        return false;
    }

    public boolean updateBgm(String member_id, String bgm_link) {
        boolean bool = miniHomepyMapper.updateBgmByMemberId(member_id, bgm_link);
        return bool;
    }

    public Map<String, Object> getMiniHomepyBoardList(String member_id) {
        Map<String, Object> topBoardList = new HashMap<>();
        topBoardList.put("topBoardList", miniHomepyMapper.getTopBoardList(member_id));
        topBoardList.put("newBoardList", miniHomepyMapper.getNewBoardList(member_id));
        System.out.println("topBoardList = " + topBoardList);
        return topBoardList;
    }

    public Map<String, Object> getAllBoardList(String memberId, String categoryOrdedBy) {
        Map<String, Object> boardListMap = new HashMap<>();
        boardListMap.put("boardListAll", miniHomepyMapper.getAllBoardList(memberId, categoryOrdedBy));
    return boardListMap;
    }
}
