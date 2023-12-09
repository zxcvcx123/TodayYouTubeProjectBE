package com.example.pj2be.mapper.adminmapper;

import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("""
        SELECT *
        FROM board
        """)
    List<BoardDTO> getBoardData();
}
