package com.example.pj2be.mapper.votemapper;

import com.example.pj2be.domain.vote.VoteCountDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VoteCountMapper {

    @Update("""
            INSERT INTO vote_count (
            vote_board_id
            )
            VALUES ( 
            #{vote_board_id}
            )
            
            """)
    int addVote(Integer id);

    @Update("""
            UPDATE vote_count
            SET voted_a = voted_a + 1
            WHERE vote_board_id = #{boardId}
            """)
    void addVoteA(Integer boardId);

    @Update("""
            UPDATE vote_count
            SET voted_b = voted_b + 1
            WHERE vote_board_id = #{boardId}
            """)
    void addVoteB(Integer boardId);

    @Update("""
            UPDATE vote_count
            SET voted_a = voted_a - 1
            WHERE vote_board_id = #{boardId}
            """)
    void minusVoteA(Integer boardId);

    @Update("""
            UPDATE vote_count
            SET voted_b = voted_b - 1
            WHERE vote_board_id = #{boardId}
            """)
    void minusVoteB(Integer boardId);


    @Select("""
            SELECT 
            id, 
            vote_board_id, 
            voted_a, 
            voted_b,
            voted_a + voted_b AS voted_all, 
            created_at
            FROM vote_count
            WHERE vote_board_id = #{boardId}
            """)
    VoteCountDTO getVoteCount(Integer boardId);


    @Select("""
            SELECT COUNT(id)
            FROM vote_check
            WHERE vote_board_id = #{vote_board_id} AND vote_member_id = #{vote_member_id}
            """)
    Integer voteCheckedInsert(VoteCountDTO voteCountDTO);

    @Insert("""
            INSERT INTO vote_check (
                        vote_board_id, 
                        vote_member_id, 
                        checked_vote_a, 
                        checked_vote_b
                        ) VALUES (
                        #{vote_board_id},
                        #{vote_member_id},
                        #{voted_a},
                        #{voted_b}
                        )
            """)
    void addVoteCheck(VoteCountDTO voteCountDTO);

    @Update("""
            UPDATE vote_check
            SET checked_vote_a = #{voted_a},
                checked_vote_b = #{voted_b}
            WHERE vote_board_id = #{vote_board_id} AND vote_member_id = #{vote_member_id}
            """)
    void voteCheckedUpdate(VoteCountDTO voteCountDTO);

    @Select("""
            SELECT
                    vc.vote_board_id AS id,
                    vc.voted_a AS voted_a,
                    vc.voted_b AS voted_b,
                    vc.voted_a + vc.voted_b AS voted_all
            FROM vote_count vc
            WHERE vc.vote_board_id = #{boardId};
            """)
    VoteCountDTO voteBoardCount(Integer boardId);


    @Select("""
            SELECT * FROM vote_check
            WHERE vote_board_id = #{vote_board_id} AND vote_member_id = #{vote_member_id}
            """)
    VoteCountDTO voteCheckedCount(VoteCountDTO voteCountDTO);
}
