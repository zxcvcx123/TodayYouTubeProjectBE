package com.example.pj2be.service.voteservice;

import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.vote.VoteDTO;
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

    // 투표 글 작성
    public void add(VoteDTO voteDTO) {

        voteMapper.add(voteDTO);

    }


    public Map<String, Object> list(PageDTO pageDTO, Integer p, String k) {

        Map<String, Object> map = new HashMap<>();

        // 페이지
        pageDTO.setPage(p);
        pageDTO.setTotalList(voteMapper.getTotal());

        // 검색
        if(k == null || k.isBlank() || k.isEmpty()){
            map.put("vote", voteMapper.list(pageDTO, "%%"));
        } else {
            map.put("vote", voteMapper.list(pageDTO, "%"+k+"%"));
        }

        map.put("page", pageDTO);

        return map;
    }

    // 투표 게시글 보기
    public VoteDTO view(Integer id) {
        return voteMapper.view(id);
    }

}
