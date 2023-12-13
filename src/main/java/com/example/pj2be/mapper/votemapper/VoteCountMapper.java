package com.example.pj2be.mapper.votemapper;

import com.example.pj2be.domain.vote.VoteCountDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteCountMapper {

    @Insert("""
            INSERT INTO vote_count (
            vote_board_id
            )
            VALUES ( 
            #{vote_board_id}
            )
            
            """)
    int addVoteA(Integer id);
}
