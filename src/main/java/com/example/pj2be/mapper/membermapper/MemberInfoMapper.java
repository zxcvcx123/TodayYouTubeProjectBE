package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberInfoMapper {

    @Select("""
                SELECT
                b.id,
                b.title,
                b.link,
                m.nickname,
                b.created_at,
                b.views,
                COUNT(DISTINCT l.id) AS countlike
            FROM board b
                     JOIN member m ON b.board_member_id = m.member_id
                     LEFT JOIN boardlike l on b.id = l.board_id
            WHERE  b.board_member_id = #{member_id}
            GROUP BY b.id
           
                        """)
    List<BoardDTO> getMyBoardList(String member_id, String categoryOrdedBy, String categoryTopics);
}
