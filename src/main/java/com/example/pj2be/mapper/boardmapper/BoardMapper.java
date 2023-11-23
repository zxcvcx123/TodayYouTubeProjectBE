package com.example.pj2be.mapper.boardmapper;

import com.example.pj2be.domain.BoardDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoardMapper {
    
    // 게시글 저장
    @Insert("""
            INSERT INTO youtube.board (
                title, content, link, board_category_code, board_member_id
            ) VALUES (
                #{title}, #{content}, #{link}, #{category}, #{writer}
            )
            """)
    int boardWrite(BoardDTO board);

    // 게시글 보기
    @Select("""
            SELECT * FROM board
            WHERE id = #{boardId}
            """)
    List<BoardDTO> getBoardById(Integer boardId);

    // 게시글 수정
    @Update("""
            UPDATE board
            SET title = #{title},
                content = #{content},
                link = #{link},
            WHERE id = #{id}
            """)
    void updateBoardById(BoardDTO board);
}
