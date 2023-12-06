package com.example.pj2be.mapper.likemapper;

import com.example.pj2be.domain.like.BoardLikeDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    int selectById(BoardLikeDTO boardLikeDTO);


    @Delete("""
        DELETE FROM boardlike
        WHERE board_id = #{board_id}
            AND member_id = #{member_id}
        """)
    int deleteById(BoardLikeDTO boardLikeDTO);

    @Insert("""
        INSERT INTO boardlike (board_id, member_id)
        VALUES (#{board_id}, #{member_id})
        """)
    int insertById(BoardLikeDTO boardLikeDTO);

    @Select("""
            SELECT member_id FROM boardlike
            WHERE board_id = #{board_id}
            """)
    List<String> getCheckListId(BoardLikeDTO boardLikeDTO);

}

