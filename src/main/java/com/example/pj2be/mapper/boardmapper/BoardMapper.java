package com.example.pj2be.mapper.boardmapper;

import com.example.pj2be.domain.BoardDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

import java.util.Map;

@Mapper
public interface BoardMapper {

    // 게시글 저장
    @Insert("""
        INSERT INTO board (title, link, content, board_category_code, board_member_id)
        VALUES (#{title}, 
                #{link}, 
                #{content}, 
                (SELECT code FROM category WHERE id = 1 ),
                (SELECT member_id FROM member WHERE  id = 1)
                )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BoardDTO board);

    @Select("""
        SELECT id, title, content, link, board_category_code, board_member_id, created_at, updated_at
        FROM board
        WHERE id = #{id}
        """)
    BoardDTO selectById(Integer id);

    @Select("""
        SELECT *
        FROM board
        """)
    List<BoardDTO> selectAll();

    @Update("""
            UPDATE board
            SET title = #{title},
                link = #{link},
                content = #{content},
                updated_at = #{updated_at}
            WHERE id = #{id}
            """)
    void update(BoardDTO board);



}
