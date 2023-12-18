package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
             INSERT INTO comment (board_id, comment, member_id, code) 
        VALUES (#{board_id}, #{comment}, #{member_id}, #{code})
            """)
    int commentInsert(CommentDTO commentDTO);


    @Select("""
            SELECT c.id,
                   c.board_id,
                   c.member_id,
                   c.comment,
                   c.created_at,       
                   c.code,         
                   m.nickname nickname,
                   (SELECT COUNT(cl.id) FROM comment_like cl WHERE cl.comment_id = c.id) count_comment_like,
                   (SELECT COUNT(cl.id) FROM comment_like cl WHERE cl.comment_id = c.id AND cl.member_id = #{member_id}) likeHeart
                                           
            FROM comment c
                     JOIN member m ON c.member_id = m.member_id
                
            WHERE board_id = #{board_id} AND code IS NULL 
            """)
    List<CommentDTO> commentSelectByBoard_id(Integer board_id, String member_id, CommentDTO commentDTO);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    int commentDeleteById(Integer comment_id);


    @Update("""
            UPDATE comment
            SET comment = #{comment}
            WHERE id = #{id}
            """)
    int commentUpdate(CommentDTO commentDTO);

// ====================== 투표 게시판 댓글 ==============
    @Insert("""
            INSERT INTO comment (board_id, comment, member_id, code) 
            VALUES (#{board_id}, #{comment}, #{member_id}, #{code})
            """)
    int VoteCommentInsert(CommentDTO commentDTO);



    @Select("""
            SELECT c.id,
                   c.board_id,
                   c.member_id,
                   c.comment,
                   c.created_at, 
                   c.code,                  
                   m.nickname nickname,
                   
                   (SELECT COUNT(cl.id) FROM comment_like cl WHERE cl.comment_id = c.id) count_comment_like,
                   (SELECT COUNT(cl.id) FROM comment_like cl WHERE cl.comment_id = c.id AND cl.member_id = #{member_id}) likeHeart

                                           
            FROM comment c
                     JOIN member m ON c.member_id = m.member_id                 
                                          
            WHERE board_id = #{board_id} AND c.code = 'C008'
            """)
    List<CommentDTO> voteCommentSelectByBoard_id(CommentDTO commentDTO);
}
