package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberInfoMapper {

    @Select("""
            SELECT
                b.title,
                b.content,
                b.created_at,
                b.board_member_id,
                
                COUNT(l.id) AS total_like
                
            FROM
                board b
                LEFT JOIN boardlike l ON b.id = l.board_id
                INNER JOIN member m ON b.board_member_id = m.member_id
            WHERE
                m.member_id = #{member_id}
            GROUP BY
                b.id
                ORDER BY  DESC;
            """)
    List<BoardDTO> getMyBoardList(String member_id);
}
