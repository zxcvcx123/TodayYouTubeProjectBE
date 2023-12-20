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
               ct.name categoryName,
               ct.name_eng,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE b.created_at >= #{startDay} AND b.created_at <= #{endDay} AND b.link LIKE '%https://%' AND is_show = 1
        GROUP BY b.id, views, b.created_at
        ORDER BY countlike desc, b.views desc, b.created_at, b.id
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
               ct.name_eng,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE b.created_at >= #{startDay} AND b.created_at <= #{endDay} AND b.link LIKE '%https://%' AND is_show = 1
        GROUP BY b.id, views, b.created_at 
        ORDER BY countlike desc, b.views desc, b.created_at, b.id
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
               ct.name_eng,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE board_category_code = #{c} AND b.created_at >= #{startDay} AND b.created_at <= #{endDay} AND b.link LIKE '%https://%' AND is_show = 1
        GROUP BY b.id, views, b.created_at
        ORDER BY countlike desc, b.views desc, b.created_at, b.id
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
               ct.name_eng,
               is_show,
               views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                     LEFT JOIN youtube.category ct on ct.code = b.board_category_code
        WHERE board_category_code = #{c} AND b.created_at >= #{startDay} AND b.created_at <= #{endDay} AND b.link LIKE '%https://%' AND is_show = 1
        GROUP BY b.id, views, b.created_at
        ORDER BY countlike desc, b.views desc, b.created_at, b.id
        LIMIT 0, 1 ;
        """)
    BoardDTO selectFirstByCategory2(String c, LocalDateTime startDay, LocalDateTime endDay);

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C002' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory2();

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C003' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory3();

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C004' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory4();

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C005' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory5();

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C006' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory6();

    @Select("""
            SELECT b.title, b.id, b.created_at,
                   b.updated_at
            FROM board b
            JOIN category c ON b.board_category_code = c.code
            WHERE board_category_code = 'C007' AND b.is_show = 1
            ORDER BY b.created_at DESC
            LIMIT 0, 5 ;
            """)
    List<BoardDTO> selectCategory7();

    @Select("""
            SELECT b.title, b.id, b.created_at,
                   b.updated_at, COUNT(DISTINCT bl.id) AS countlike
            FROM board b
            JOIN boardlike bl ON b.id = bl.board_id
            WHERE b.is_show = 1 AND board_category_code != 'C001' AND b.is_show = 1
            GROUP BY b.id, b.views
            ORDER BY COUNT(DISTINCT bl.id) DESC, b.views DESC
            LIMIT 5;
            """)
    List<BoardDTO> selectRecommendList();

    @Select("""
            SELECT title, b.id, b.created_at,
                   b.updated_at, b.views
            FROM board b
            WHERE b.is_show = 1 AND board_category_code != 'C001' AND b.is_show = 1
            ORDER BY b.views DESC, b.created_at DESC
            LIMIT 0, 5;
            """)
    List<BoardDTO> selectHitsList();
}
