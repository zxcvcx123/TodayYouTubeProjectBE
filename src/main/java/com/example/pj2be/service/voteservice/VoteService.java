package com.example.pj2be.service.voteservice;

import com.example.pj2be.domain.vote.VoteDTO;
import com.example.pj2be.mapper.votemapper.VoteCountMapper;
import com.example.pj2be.mapper.votemapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class VoteService {

    private final VoteMapper voteMapper;
    private final VoteCountMapper voteCountMapper;

    public void add(VoteDTO voteDTO) {

        voteMapper.add(voteDTO);
        voteCountMapper.addVote(voteDTO.getId());

    }

    public VoteDTO view(Integer id){
        return voteMapper.view(id);
    }
}
