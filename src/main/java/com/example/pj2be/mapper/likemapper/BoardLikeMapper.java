package com.example.pj2be.mapper.likemapper;

import com.example.pj2be.domain.like.BoardLikeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardLikeMapper {

    @Select("""
        SELECT COUNT(id)
        FROM boardlike
        WHERE board_id = #{board_id}
        """)
    int countLikeByBoardId(BoardLikeDTO dto);


    @Select("""
        SELECT COUNT(id)
        FROM boardlike
        WHERE member_id = #{member_id} AND board_id = #{board_id}
        """)
    int selectByTestId(BoardLikeDTO dto);
}
