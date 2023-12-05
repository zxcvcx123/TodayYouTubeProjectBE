package com.example.pj2be.mapper.mainmapper;

import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MainMapper {


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
        WHERE board_category_code = #{c} AND b.created_at >= #{rankingTime}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 4 OFFSET 1;
        """)
    List<BoardDTO> selectOtherByCategory(String c, LocalDateTime rankingTime);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
        WHERE board_category_code = #{c} AND b.created_at >= #{rankingTime}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 0, 1 ;
        """)
    List<BoardDTO> selectFirstByCategory(String c, LocalDateTime rankingTime);

    
    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
        WHERE b.created_at >= #{rankingTime}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 0, 1 ;
        """)
    List<BoardDTO> selectFirstByAll(LocalDateTime rankingTime);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
        WHERE b.created_at >= #{rankingTime}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 4 OFFSET 1;
        """)
    List<BoardDTO> selectOtherByALl(LocalDateTime rankingTime);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               ct.name categoryName,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE b.created_at >= #{startDay} AND b.created_at <= #{endDay}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 4 OFFSET 1;
        """)
    List<BoardDTO> selectOtherByALl2(LocalDateTime startDay, LocalDateTime endDay);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               ct.name categoryName,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE b.created_at >= #{startDay} AND b.created_at <= #{endDay}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 0, 1 ;
        """)
    BoardDTO selectFirstByAll2(LocalDateTime startDay, LocalDateTime endDay);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               ct.name categoryName,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE board_category_code = #{c} AND b.created_at >= #{startDay} AND b.created_at <= #{endDay}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 4 OFFSET 1;
        """)
    List<BoardDTO> selectOtherByCategory2(String c, LocalDateTime startDay, LocalDateTime endDay);


    @Select("""
        SELECT b.id,
               b.title,
               b.content,
               b.link,
               b.board_category_code,
               b.board_member_id,
               b.created_at,
               b.updated_at,
               COUNT(DISTINCT bl.id) countlike,
               COUNT(DISTINCT c.id) count_comment,
               ct.name categoryName,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE board_category_code = #{c} AND b.created_at >= #{startDay} AND b.created_at <= #{endDay}
        GROUP BY b.id
        ORDER BY countlike desc
        LIMIT 0, 1 ;
        """)
    BoardDTO selectFirstByCategory2(String c, LocalDateTime startDay, LocalDateTime endDay);
}
