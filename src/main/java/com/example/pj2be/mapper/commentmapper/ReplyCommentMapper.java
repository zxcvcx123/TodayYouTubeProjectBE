package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReplyCommentMapper {

    @Insert("""
            INSERT INTO reply_comment (comment_id, reply_comment, member_id)
            VALUES (#{reply_comment.comment_id}, #{reply_comment.reply_comment}, #{reply_comment.member_id})
            """)
    int reply_commentInsert(ReplyCommentDTO reply_comment, String member_id);

    @Select("""
            SELECT r.id,
                   r.comment_id,
                   r.member_id,
                   r.reply_comment,
                   r.created_at,
                   m.nickname
                
            FROM reply_comment r
            JOIN member m ON r.member_id = m.member_id
            
          
            WHERE comment_id = #{comment_id}
            """)
    List<ReplyCommentDTO> reply_commentSelectByComment_id(Integer comment_id);

    @Delete("""
            DELETE FROM reply_comment
            WHERE id = #{id}
            """)
    int deleteByReply_id(Integer reply_id);

    @Update("""
            UPDATE reply_comment
            SET reply_comment = #{reply_comment}
            WHERE id = #{id}
            """)
    int reply_commentUpdate(ReplyCommentDTO reply_comment);

    @Delete("""
            DELETE FROM reply_comment
            WHERE comment_id = #{comment_id}
            """)
    int DeleteByCommentId(Integer comment_id);

}
