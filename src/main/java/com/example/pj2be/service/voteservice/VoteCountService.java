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


    private void addVoteA(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.addVoteA(boardId);
    }


    private void addVoteB(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.addVoteB(boardId);

    }

    private void minusVoteA(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.minusVoteA(boardId);


    }


    private void minusVoteB(VoteCountDTO voteCountDTO) {

        Integer boardId = voteCountDTO.getVote_board_id();

        voteCountMapper.minusVoteB(boardId);


    }


    public VoteCountDTO voteGetCount(Integer boardId) {
        return voteCountMapper.voteBoardCount(boardId);
    }

    public VoteCountDTO voteCheck(VoteCountDTO voteCountDTO, String checked) {
        System.out.println("vote check 동작");

        System.out.println("조회 값: " + voteCountMapper.voteCheckedInsert(voteCountDTO));
        
        // 로그인 사용자 없는 경우 아무값도 없는거 반환
        if(voteCountDTO.getVote_member_id().isEmpty() || voteCountDTO.getVote_member_id().isBlank()){
            VoteCountDTO dto = new VoteCountDTO();

            dto.setChecked_vote_a(0);
            dto.setChecked_vote_b(0);
            dto.setVoted_a(0);
            dto.setVoted_b(0);

            return dto;
        }

        if (voteCountMapper.voteCheckedInsert(voteCountDTO) == 0) {
            if (checked == "voteA") {
                voteCountDTO.setVoted_a(1);
                voteCountDTO.setVoted_b(0);
                addVoteA(voteCountDTO);

                voteCountMapper.addVoteCheck(voteCountDTO);
            }

            if (checked == "voteB") {
                voteCountDTO.setVoted_a(0);
                voteCountDTO.setVoted_b(1);
                addVoteB(voteCountDTO);
                voteCountMapper.addVoteCheck(voteCountDTO);
            }

        } else {
            if (checked == "voteA") {
                voteCountDTO.setVoted_a(1);
                voteCountDTO.setVoted_b(0);
                addVoteA(voteCountDTO);
                minusVoteB(voteCountDTO);
                voteCountMapper.voteCheckedUpdate(voteCountDTO);
            }

            if (checked == "voteB") {
                voteCountDTO.setVoted_a(0);
                voteCountDTO.setVoted_b(1);
                addVoteB(voteCountDTO);
                minusVoteA(voteCountDTO);

                voteCountMapper.voteCheckedUpdate(voteCountDTO);
            }
        }
        return voteCountMapper.voteCheckedCount(voteCountDTO);
    }


}
