package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.member.MemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Insert("""
            INSERT INTO member(
            member_id, 
            password, 
            nickname, 
            email, 
            phone_number, 
           gender, 
           birth_date) 
           VALUES (
           #{member_id},
           #{password},
           #{nickname},
           #{email},
           #{phone_number},
           #{gender},
           #{birth_date}
           )
            """)
    int signup(MemberDTO memberDTO);

    @Select("""
            SELECT * FROM member
            WHERE role_id = 2 AND member_id = #{member_id}
            """)
    String select_member_id(String memberId);
    @Select("""
            SELECT * FROM member
            WHERE role_id = 2 AND nickname = #{nickname}
            """)
    String select_nickname(String nickname);

    @Select("""
            SELECT * FROM member
            WHERE role_id = 2 AND email = #{email}
            """)
    String select_Email(String email);
}
