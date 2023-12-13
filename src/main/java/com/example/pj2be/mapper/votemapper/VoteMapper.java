package com.example.pj2be.mapper.votemapper;

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
                   r.role_name AS rolename
            FROM vote v LEFT JOIN member m
                            ON v.vote_member_id = m.member_id
                        LEFT JOIN roles r
                            ON m.role_id = r.role_id
            WHERE v.id = #{id}
            """)
    VoteDTO view(Integer id);
}
