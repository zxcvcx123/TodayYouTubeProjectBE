package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.member.MemberDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

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
    String select_member_id(String memberId);
    @Select("""
            SELECT nickname FROM member
            WHERE role_id = 2 AND nickname = #{nickname}
            """)
    String select_nickname(String nickname);

    @Select("""
            SELECT email FROM member
            WHERE role_id = 2 AND email = #{email}
            """)
    String select_Email(String email);

    @Select("""
            SELECT * FROM member
            WHERE member_id = #{member_id};
            """)
    Optional<MemberDTO> findByMemberId(String member_id);


    @Select("""
        
            SELECT COUNT(bl.board_id) AS total_like, m.id, m.member_id, m.nickname, m.email, m.phone_number, r.role_name
            FROM member m
                     LEFT JOIN roles r ON m.role_id = r.role_id
                    LEFT JOIN board b ON b.board_member_id = m.member_id
                     LEFT JOIN boardlike bl ON b.id = bl.board_id
            WHERE m.id = 31
            GROUP BY m.member_id = #{member_id};
                """)
    MemberDTO findLoginInfoByMemberId(String member_id);

}
