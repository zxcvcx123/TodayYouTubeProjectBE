package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
             INSERT INTO comment (board_id, comment, member_id) 
        VALUES (#{board_id}, #{comment}, #{member_id})
            """)
    int insert(CommentDTO comment);


    @Select("""
            SELECT c.id,
                   c.board_id,
                   c.member_id,
                   c.comment,
                   c.created_at,
                   m.nickname nickname
            FROM comment c
                     JOIN member m ON c.member_id = m.member_id
            WHERE board_id = #{board_id}
            """)
    List<CommentDTO> selectByBoard_id(Integer boardId);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    int deleteById(Integer id);


    @Update("""
            UPDATE comment
            SET comment = #{comment}
            WHERE id = #{id}
            """)
    int update(CommentDTO comment);
}
