package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.member.MemberDTO;
import org.apache.ibatis.annotations.*;

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
    boolean signup(MemberDTO memberDTO);

    @Select("""
            SELECT member_id FROM member
            WHERE member_id = #{memberId}
            """)
    String select_member_id(String memberId);
    @Select("""
            SELECT nickname FROM member
            WHERE nickname = #{nickname}
            """)
    String select_nickname(String nickname);

    @Select("""
            SELECT email FROM member
            WHERE email = #{email}
            """)
    String select_Email(String email);

    @Select("""
            SELECT * FROM member
            WHERE member_id = #{member_id};
            """)
    Optional<MemberDTO> findByMemberId(String member_id);


    @Select("""
        
                 SELECT 
                 (SELECT COUNT(bl2.id) 
                 FROM boardlike bl2 
                 LEFT JOIN board b2 ON bl2.board_id = b2.id 
                 WHERE b2.board_member_id = #{member_id}) AS total_like,
                (SELECT SUM( views) 
                FROM board 
                WHERE board_member_id = #{member_id}) AS total_views,
                (SELECT COUNT(id) 
                FROM board 
                WHERE board_member_id = #{member_id}) AS total_board,
                (SELECT COUNT(c2.id) 
                FROM comment c2
                                LEFT JOIN member m2
                                ON c2.member_id = m2.member_id
                                 WHERE c2.member_id = #{member_id}) AS total_comment,
               (SELECT mp2.image_name FROM memberprofileimagefile mp2 
               LEFT JOIN member m3 ON mp2.member_id = m3.member_id
                WHERE mp2.member_id =#{member_id} ORDER BY mp2.id DESC LIMIT 0,1)AS image_name,
                (SELECT mp2.id FROM memberprofileimagefile mp2 
                LEFT JOIN member m3 ON mp2.member_id = m3.member_id
                 WHERE mp2.member_id = #{member_id} 
                 ORDER BY mp2.id DESC LIMIT 0,1)AS image_id,
            m.id, m.member_id, m.nickname, m.email, m.phone_number,
                               r.role_name, m.gender, m.birth_date
            FROM member m
                     LEFT JOIN memberprofileimagefile mp ON m.member_id = mp.member_id
                     LEFT JOIN roles r ON m.role_id = r.role_id
                     LEFT JOIN board b ON b.board_member_id = m.member_id
                     LEFT JOIN boardlike bl ON b.id = bl.board_id
                     LEFT JOIN comment c ON c.board_id = b.id
            WHERE m.member_id = #{member_id}
            GROUP BY m.id, m.member_id, m.nickname, m.email, m.phone_number, r.role_name, m.gender, m.
                birth_date;
                    
                    
                    
                    """)
    MemberDTO findLoginInfoByMemberId(String member_id);

    @Update("""
        UPDATE member
        SET role_id = '11'
        WHERE member_id = #{memberId}
        """)
    int changeRoleToSuspension(String memberId);

    @Update("""
        UPDATE member
        SET role_id = '2'
        WHERE member_id = #{member_id}
        """)
    void updateByReleaseId(String member_id);

    @Update("""
        UPDATE member
        SET role_id = '12'
        WHERE member_id = #{memberId}
        """)
    boolean withdrawalByMemberId(String memberId);

    @Insert("""
            INSERT INTO member_login_valid (member_id, password) 
            VALUES (#{memberId},#{encryptPassword})
            """)
    void updateMemberLoginInfoForValid(String memberId, String encryptPassword);

    @Select("""
            SELECT password FROM member_login_valid
            """)
    String getMemberLoginValidInfo(String memberId);

    @Delete("""
            DELETE FROM member_login_valid WHERE member_id = #{memberId}
            """)
    boolean deleteMemberLoginInfoForValid(String memberId);
}


