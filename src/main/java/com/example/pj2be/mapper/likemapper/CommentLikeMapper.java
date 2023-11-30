package com.example.pj2be.mapper.likemapper;

import com.example.pj2be.domain.like.CommentLikeDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentLikeMapper {
    

    @Delete("""
            DELETE FROM comment_like
            WHERE comment_id = #{comment_id}
            AND board_id = #{board_id}
            AND member_id = #{member_id}
            """)
    int delete(CommentLikeDTO commentLike);

    @Insert("""
            INSERT INTO comment_like (board_id, member_id, comment_id)
            VALUES (#{board_id}, #{member_id}, #{comment_id})
            """)
    int insert(CommentLikeDTO commentLike);

    @Select("""
            SELECT COUNT(id) FROM comment_like
            WHERE comment_id = #{comment_id}
            """)
    int countByCommentId(Integer comment_id);
}
