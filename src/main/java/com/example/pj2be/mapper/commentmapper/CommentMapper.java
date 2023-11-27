package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.CommentDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    @Insert("""
             INSERT INTO comment (board_id, comment, member_id) 
        VALUES (#{board_id}, #{comment}, #{member_id})
            """)
    int insert(CommentDTO comment);
}
