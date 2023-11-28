package com.example.pj2be.mapper.commentmapper;

import com.example.pj2be.domain.comment.ReplyCommentDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyCommentMapper {

    @Insert("""
            INSERT INTO reply_comment (comment_id, reply_comment, member_id)
            VALUES (#{comment_id}, #{reply_comment}, #{member_id})
            """)
    int insert(ReplyCommentDTO replycomment);
}
