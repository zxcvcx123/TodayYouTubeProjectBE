package com.example.pj2be.mapper.adminmapper;

import com.example.pj2be.domain.admin.BoardDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("""
        SELECT
            c.name_eng,
            COUNT(board_category_code) AS count_category_board,
            SUM(views) AS count_category_view,
            SUM(CASE WHEN m.gender = 'm' THEN 1 ELSE 0 END) AS count_category_members_man,
            SUM(CASE WHEN m.gender = 'w' THEN 1 ELSE 0 END) AS count_category_members_woman
        FROM board b
            JOIN category c on b.board_category_code = c.code
            JOIN member m on b.board_member_id = m.member_id
        GROUP BY board_category_code
        ORDER BY board_category_code;
        """)
    List<BoardDataDTO> getBoardData();
}
