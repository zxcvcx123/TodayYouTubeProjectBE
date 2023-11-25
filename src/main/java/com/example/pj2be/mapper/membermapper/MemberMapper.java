package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.MemberDTO;
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
            SELECT member_id FROM member
            WHERE role_id = 2 AND member_id = #{member_id}
            """)
    boolean select_member_id(String memberId);
    @Select("""
            SELECT nickname FROM member
            WHERE role_id = 2 AND nickname = #{nickname}
            """)
    boolean select_nickname(String nickname);

    @Select("""
            SELECT email FROM member
            WHERE role_id = 2 AND email = #{email}
            """)
    boolean select_Email(String email);
}
