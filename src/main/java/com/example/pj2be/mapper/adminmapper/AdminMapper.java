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
            COUNT(views) AS count_category_view
        FROM board b
            JOIN category c on b.board_category_code = c.code
        GROUP BY board_category_code
        ORDER BY board_category_code;
        """)
    List<BoardDataDTO> getBoardData();
}
