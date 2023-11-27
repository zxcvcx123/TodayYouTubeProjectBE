package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.CommentDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
             INSERT INTO comment (board_id, comment, member_id) 
        VALUES (#{board_id}, #{comment}, #{member_id})
            """)
    int insert(CommentDTO comment);


    @Select("""
            SELECT * FROM comment
            WHERE board_id = #{board_id}
            """)
    List<CommentDTO> selectByBoard_id(Integer boardId);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    int deleteById(Integer id);
}
