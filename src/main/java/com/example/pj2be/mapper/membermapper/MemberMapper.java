package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.MemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
}
