package com.example.pj2be.service.voteservice;

import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.mapper.votemapper.VoteCountMapper;
import com.example.pj2be.mapper.votemapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.awscore.util.SignerOverrideUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class VoteService {

    private final VoteMapper voteMapper;
    private final VoteCountMapper voteCountMapper;

    // 투표 글 작성
    public void add(VoteDTO voteDTO) {

        voteMapper.add(voteDTO);
        voteCountMapper.addVote(voteDTO.getId());

    }


    // 투표 게시글 보기
    public VoteDTO view(Integer id) {
        return voteMapper.view(id);
    }

    // 게시글 리스트 보기
    public Map<String, Object> list(PageDTO pageDTO, Integer p, String k) {

        Map<String, Object> map = new HashMap<>();

        // 페이지
        pageDTO.setPage(p);

        // 검색
        if (k == null || k.isBlank() || k.isEmpty()) {
            pageDTO.setTotalList(voteMapper.getTotal("%%"));
            map.put("vote", voteMapper.list(pageDTO, "%%"));
        } else {
            pageDTO.setTotalList(voteMapper.getTotal("%" + k + "%"));
            map.put("vote", voteMapper.list(pageDTO, "%" + k + "%"));
        }

        map.put("page", pageDTO);
        System.out.println("페이지: " + pageDTO);
        System.out.println("내용: " + map.get("vote"));
        return map;
    }

    public VoteCountDTO voteHistory(VoteCountDTO voteCountDTO) {

        VoteCountDTO list = voteMapper.voteHistory(voteCountDTO);


        if (list == null) {
            VoteCountDTO list1 = new VoteCountDTO();
            list1.setChecked_vote_not(1);
            return list1;
        }
      return list;
    }
}
