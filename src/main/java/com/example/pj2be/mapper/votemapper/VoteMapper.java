package com.example.pj2be.mapper.votemapper;

import com.example.pj2be.domain.vote.VoteDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VoteMapper {


    @Insert("""
            INSERT INTO vote (
                vote_member_id,
                title,
                link_a,
                link_b,
                content,
                name_eng
            ) VALUES (
                #{vote_member_id},
                #{title},
                #{link_a},
                #{link_b},
                #{content},
                #{name_eng}
            )
            """)
    void add(VoteDTO voteDTO);
}
