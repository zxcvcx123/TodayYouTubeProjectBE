package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyCommentMapper {

    @Insert("""
            INSERT INTO reply_comment (comment_id, reply_comment, member_id)
            VALUES (#{comment_id}, #{reply_comment}, #{member_id})
            """)
    int insert(ReplyCommentDTO replycomment);

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
    List<ReplyCommentDTO> selectByComment_id(Integer comment_id);
}
