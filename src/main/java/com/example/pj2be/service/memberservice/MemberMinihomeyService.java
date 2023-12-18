package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyCommentDTO;
import com.example.pj2be.domain.member.YoutuberInfoDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyDTO;
import com.example.pj2be.mapper.membermapper.MiniHomepyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        topBoardList.put("favoriteBoardList", miniHomepyMapper.getFavoriteBoardList(member_id));
        return topBoardList;
    }

    public Map<String, Object> getAllBoardList(String memberId, String categoryOrdedBy, String searchingKeyword) {
        Map<String, Object> boardListMap = new HashMap<>();
        boardListMap.put("boardListAll", miniHomepyMapper.getAllBoardList(memberId, categoryOrdedBy, "%"+searchingKeyword+"%"));
    return boardListMap;
    }

    public boolean addYoutuberInfo(YoutuberInfoDTO youtuberInfoDTO) {
        String memberId = youtuberInfoDTO.getMember_id();
        String title = youtuberInfoDTO.getTitle();
        String customUrl = youtuberInfoDTO.getCustomUrl();
        LocalDateTime publishedAt = youtuberInfoDTO.getPublishedAt();
        String thumbnails = youtuberInfoDTO.getThumbnails();
        String description = youtuberInfoDTO.getDescription();
        String formattedVideoCount = formatNumber(Long.parseLong(youtuberInfoDTO.getVideoCount()));
        String formattedSubscriberCount = formatNumber(Long.parseLong(youtuberInfoDTO.getSubscriberCount()));
        String formattedViewCount = formatNumber(Long.parseLong(youtuberInfoDTO.getViewCount()));
        String country = youtuberInfoDTO.getCountry();
        return miniHomepyMapper.addYoutuberInfoByMemberId(memberId, title, customUrl, publishedAt, thumbnails, description, formattedVideoCount, formattedSubscriberCount,formattedViewCount, country) == 1;
    }

    public String formatNumber(long number) {
//        String formattedVideoCount = formatNumber(Long.parseLong(youtuberInfoDTO.getVideoCount()));
//        String formattedSubscriberCount = formatNumber(Long.parseLong(youtuberInfoDTO.getSubscriberCount()));
//        String formattedViewCount = formatNumber(Long.parseLong(youtuberInfoDTO.getViewCount()));
//
//        String videoCount = youtuberInfoDTO.getVideoCount();
//        String subscriberCount = youtuberInfoDTO.getSubscriberCount();
//        String viewCount = youtuberInfoDTO.getViewCount();
//        videoCount, subscriberCount,viewCount,
        if (number < 10000) {
            return String.valueOf(number);
        } else if (number < 100000000) {
            double result = number / 10000.0;
            return String.format("%.1f만", result).replaceAll("\\.0", "");
        } else {
            double result = number / 100000000.0;
            return String.format("%.1f억", result).replaceAll("\\.0", "");
        }
    }

    public Map<String, Object> getYoutuberInfo(String memberId) {
        Map<String, Object> youtuberInfo = new HashMap<>();
        youtuberInfo.put("youtuberInfo", miniHomepyMapper.getYoutuberInfoList(memberId));
        return youtuberInfo;
    }

    public boolean addMiniHomepyComment(MiniHomepyCommentDTO miniHomepyCommentDTO) {
        String memberId = miniHomepyCommentDTO.getMember_id();
        String comment = miniHomepyCommentDTO.getComment();
        String imageUrl = miniHomepyCommentDTO.getImage_url();
        int homepyId = miniHomepyCommentDTO.getHomepy_id();
        String nickname = miniHomepyCommentDTO.getNickname();
        String roleName = miniHomepyCommentDTO.getRole_name();
        return miniHomepyMapper.addMiniHomepyCommentById(memberId, comment, imageUrl, homepyId,nickname, roleName );
    }

    public Map<String, Object> getMiniHomepyComment(Integer homepy_id) {
        Map<String, Object> getComment = new HashMap<>();
        getComment.put("commentList", miniHomepyMapper.getMiniHomepyCommentByHomepyId(homepy_id));

        return getComment;
    }

    public boolean deleteComment(Integer id) {
        return miniHomepyMapper.deleteCommentById(id);
    }

    public boolean updateComment(Integer id, String comment) {
        return miniHomepyMapper.updateCommentById(id, comment);
    }

    public boolean deleteYoutuberInfo(Integer id) {
        return miniHomepyMapper.deleteYoutuberInfo(id);
    }
}
