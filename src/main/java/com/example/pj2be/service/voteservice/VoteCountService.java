package com.example.pj2be.service.voteservice;

import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.mapper.votemapper.VoteCountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class VoteCountService {

    private final VoteCountMapper voteCountMapper;

    public void addVoteA(Integer boardId) {
        voteCountMapper.addVoteA(boardId);

    }


}
