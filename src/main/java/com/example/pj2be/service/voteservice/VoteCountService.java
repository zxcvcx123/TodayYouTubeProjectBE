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


    public VoteCountDTO addVoteA(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.addVoteA(boardId);

        return voteCountMapper.getVoteCount(boardId);
    }


    public VoteCountDTO addVoteB(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.addVoteB(boardId);

        return voteCountMapper.getVoteCount(boardId);
    }


    public VoteCountDTO voteGetCount(VoteCountDTO voteCountDTO) {
        return voteCountMapper.voteBoardCount(voteCountDTO);
    }

    public VoteCountDTO voteCheck(VoteCountDTO voteCountDTO, String checked) {
        System.out.println("vote check 동작");

        System.out.println("조회 값: " + voteCountMapper.voteCheckedInsert(voteCountDTO));
        if (voteCountMapper.voteCheckedInsert(voteCountDTO) == 0) {
            if (checked == "voteA") {
                voteCountDTO.setVoted_a(1);
                voteCountDTO.setVoted_b(0);
                voteCountMapper.addVoteCheck(voteCountDTO);
            }

            if (checked == "voteB") {
                voteCountDTO.setVoted_a(0);
                voteCountDTO.setVoted_b(1);
                voteCountMapper.addVoteCheck(voteCountDTO);
            }

        }

        if (voteCountMapper.voteCheckedInsert(voteCountDTO) == 1) {
            if (checked == "voteA") {
                voteCountDTO.setVoted_a(1);
                voteCountDTO.setVoted_b(0);
                voteCountMapper.voteCheckedUpdate(voteCountDTO);
            }

            if (checked == "voteB") {
                voteCountDTO.setVoted_a(0);
                voteCountDTO.setVoted_b(1);
                voteCountMapper.voteCheckedUpdate(voteCountDTO);
            }
        }
        return voteCountMapper.voteCheckedCount(voteCountDTO);
    }


}
