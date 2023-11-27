package com.example.pj2be.mapper.likemapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentLikeMapper {

    @Select("""
        SELECT *
        FROM commentlike
        WHERE board_id = #{id}
        """)
    void selectByBoardId(Integer id, String memberId);
}
