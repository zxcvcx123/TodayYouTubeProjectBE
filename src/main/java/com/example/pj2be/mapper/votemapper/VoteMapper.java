package com.example.pj2be.mapper.votemapper;

import com.example.pj2be.domain.page.PageDTO;
import com.example.pj2be.domain.vote.VoteCountDTO;
import com.example.pj2be.domain.vote.VoteDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(VoteDTO voteDTO);


    @Select("""
            SELECT v.id AS id,
                   v.vote_member_id AS vote_member_id,
                   v.title AS title,
                   v.link_a AS link_a,
                   V.link_b AS link_b,
                   v.content AS content,
                   v.name_eng AS name_eng,
                   v.created_at AS created_at,
                   m.nickname AS nickname,
                   vc.voted_a AS voted_a,
                   vc.voted_b AS voted_b,
                   vc.voted_a + vc.voted_b AS voted_all,
            
                   r.role_name AS rolename
            FROM vote v LEFT JOIN member m
                                  ON v.vote_member_id = m.member_id
                        LEFT JOIN roles r
                                  ON m.role_id = r.role_id
                        LEFT JOIN vote_count vc
                                  ON v.id = vc.vote_board_id
            WHERE v.id = #{id};
            """)
    VoteDTO view(Integer id);

    @Select("""
            SELECT v.id AS id,
                   vote_member_id,
                   title,
                   link_a,
                   link_b,
                   content,
                   name_eng,
                   v.created_at AS created_at,
                   voted_a,
                   voted_b,
                   voted_a + voted_b AS voted_all,
                   nickname
            FROM vote v LEFT JOIN vote_count vc
                            ON v.id = vc.vote_board_id
                        LEFT JOIN member m
                            ON v.vote_member_id = m.member_id
            WHERE v.title LIKE #{k}
            ORDER BY v.id DESC
            LIMIT #{pageDTO.limitNowPage}, #{pageDTO.limitList}
            """)
    List<VoteDTO> list(PageDTO pageDTO, String k);



    @Select("""
            SELECT COUNT(v.id)
            FROM vote v LEFT JOIN vote_count vc
                                  ON v.id = vc.vote_board_id
                        LEFT JOIN member m
                                  ON v.vote_member_id = m.member_id
            WHERE v.title LIKE #{k}
            """)
    Integer getTotal(String k);


    @Select("""
            SELECT *
            FROM vote_check
            WHERE vote_board_id = #{vote_board_id} AND vote_member_id = #{vote_member_id} 
            """)
    VoteCountDTO voteHistory(VoteCountDTO voteCountDTO);
}
