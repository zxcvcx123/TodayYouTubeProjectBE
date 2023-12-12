package com.example.pj2be.mapper;

import com.example.pj2be.domain.allsearch.AllSearchDTO;
import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AllSearchMapper {

    @Select("""
        SELECT  category.name as resultCategory,
                COUNT(distinct board.id) as categoryCount
        FROM board
        JOIN category ON board.board_category_code = category.code
        WHERE board.title LIKE #{allKeyword} OR board.content LIKE #{allKeyword}
        GROUP BY category.name;
        """)
    List<AllSearchDTO> selectByallKeyword(String allKeyword);


    @Select("""
        SELECT  category.name as resultCategory,
                board.title,
                board.content,
                COUNT(distinct board.id) as categoryCount,
                board.created_at
        FROM board
                 LEFT JOIN category ON board.board_category_code = category.code
        WHERE (board.title LIKE #{allKeyword} OR board.content LIKE #{allKeyword}) AND category.name = #{c}
        GROUP BY board.id, category.name, board.created_at
        ORDER BY board.created_at DESC
        LIMIT 3;
        """)
    List<AllSearchDTO> selectCategoryByAllKeyword(String allKeyword, String c);
}
