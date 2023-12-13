package com.example.pj2be.mapper.adminmapper;

import com.example.pj2be.domain.admin.AdminMemberActiveBoardDTO;
import com.example.pj2be.domain.admin.AdminMemberDTO;
import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {

    @Select("""
        SELECT COUNT(*)
        FROM member
        WHERE member_id LIKE #{mid}
        """)
    int selectAll(String mid);

    @Select("""
            SELECT m.id,
                   m.member_id,
                   m.nickname,
                   m.email,
                   m.phone_number,
                   m.created_at,
                   r.role_name,
                   m.gender,
                   m.birth_date
                 FROM member m
            JOIN youtube.roles r on m.role_id = r.role_id
            WHERE member_id LIKE #{mid}
            ORDER BY r.role_name DESC, created_at DESC
            LIMIT #{from}, 20;
            """)
    List<AdminMemberDTO> selectAllMember(Integer from, String mid);

    @Select("""
           SELECT m.id,
                   m.member_id,
                   m.nickname,
                   m.email,
                   m.phone_number,
                   m.created_at,
                   r.role_name,
                   m.gender,
                   m.birth_date,
                    COUNT(DISTINCT bl.id) countlike,
                    COUNT(DISTINCT b.id) countboard,
                    COUNT(DISTINCT c.id) countcomment,
                    COUNT(DISTINCT rc.id) countcommentreply
            FROM member m
                LEFT JOIN youtube.roles r on m.role_id = r.role_id
                LEFT JOIN boardlike bl on m.member_id = bl.member_id
                LEFT JOIN youtube.board b on m.member_id = b.board_member_id
                LEFT JOIN youtube.comment c on m.member_id = c.member_id
                LEFT JOIN youtube.reply_comment rc on m.member_id = rc.member_id
                 
            WHERE m.member_id = #{memberId}
            GROUP BY m.id
            """)
    AdminMemberDTO selectByMemberId(String memberId);

    @Select("""
        SELECT name,
            COUNT(category.name) countactive
        FROM board
                 LEFT JOIN category on board.board_category_code = category.code
        WHERE board_member_id = #{memberId}
        GROUP BY name
        """)
    List<AdminMemberActiveBoardDTO> selectActiveBoard(String memberId);


    @Select("""
        SELECT  b.id,
                b.link,
                b.title,
                b.board_member_id,
                b.created_at,
                COUNT(b.id) countlike,
                b.views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
        WHERE board_member_id = #{memberId}
        GROUP BY b.id;
        """)
    List<BoardDTO> selectBoardList(String memberId);


    @Select("""
        SELECT c.id,
               c.board_id,
               b.title,
               c.comment,
               c.created_at,
               COUNT(cl.id) countcommentlike
            FROM comment c
        JOIN youtube.board b on c.board_id = b.id
        LEFT JOIN youtube.comment_like cl on c.id = cl.comment_id
        WHERE board_member_id = #{memberId}
        GROUP BY c.id, c.created_at
        ORDER BY c.created_at
        """)
    List<AdminMemberActiveBoardDTO> selectCommentList(String memberId);
}
