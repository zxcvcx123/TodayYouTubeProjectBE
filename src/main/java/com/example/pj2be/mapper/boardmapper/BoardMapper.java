package com.example.pj2be.mapper.boardmapper;

import com.example.pj2be.domain.BoardDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    void insert(BoardDTO board);

    @Select("""
        SELECT b.id, title, content, link, board_category_code, board_member_id, created_at, updated_at, COUNT(DISTINCT bl.id) countlike
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
        WHERE b.id = #{id}
        """)
    BoardDTO selectById(Integer id);

    @Select("""
        SELECT b.id,
                b.title,
                b.content,
                b.link,
                b.board_category_code,
                b.board_member_id,
                b.created_at,
                b.updated_at,
                COUNT(DISTINCT bl.id) countlike
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
        GROUP BY b.id
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
