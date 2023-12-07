package com.example.pj2be.mapper.boardmapper;

import com.example.pj2be.domain.board.BoardDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 저장
    // TODO : 코드, 멤버ID 등 작성시 로그인 정보와 연동 되도록 하기.
    @Insert("""
        INSERT INTO board (title, link, content, board_category_code, board_member_id)
        VALUES (#{title}, 
                #{link}, 
                #{content}, 
                (SELECT code FROM category WHERE id = 1 ),
                #{board_member_id}
                )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BoardDTO board);

    // 게시글 보기
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
               COUNT(DISTINCT c.comment) count_comment, 
              
               b.is_show,
               b.views
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                    
        WHERE b.id = #{id}
        GROUP BY b.id ORDER BY b.id DESC;
        """)
    BoardDTO selectById(Integer id);

    // 게시글 리스트
    @Select("""
        <script>
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
                     JOIN category ON b.board_category_code = category.code
        <where>
            <if test="type == 'all'">
                AND (
                    b.title like #{keyword} OR
                    b.content like #{keyword} OR
                    b.board_member_id like #{keyword}
                )
            </if>
            <if test="type == 'title'">
                AND b.title like #{keyword}
            </if>
            <if test="type == 'content'">
                 AND b.content like #{keyword}
            </if>
            <if test="type == 'board_member_id'">
                 AND b.board_member_id like #{keyword}
            </if>
            <if test="category != null">
                 AND category.name_eng = #{category}
            </if>
        </where>
            
        GROUP BY b.id
        ORDER BY b.id DESC
        LIMIT #{from}, #{slice}
        </script>
        """)
    List<BoardDTO> selectAll(Integer from, Integer slice, String keyword, String type, String category);

    // 게시글 수정
    // BoardEditDTO 내부에 BoardDTO가 있음.
    @Update("""
            UPDATE board
            SET title = #{title},
                link = #{link},
                content = #{content},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    void update(BoardDTO board);


    // 게시글 삭제 (Update 방식)
    @Update("""
        UPDATE board
        SET is_show = false
        WHERE id = #{id}
        """)
    void remove(Integer id);


    // 전체페이지 조회
    @Select("""
        <script>
        SELECT COUNT(*)
        FROM board b
            JOIN category c on c.code = b.board_category_code
        <where>
            <if test="type == 'all'">
                AND (
                    b.title like #{keyword} OR
                    b.content like #{keyword} OR
                    b.board_member_id like #{keyword}
                )
            </if>
            <if test="type == 'title'">
                AND b.title like #{keyword}
            </if>
            <if test="type == 'content'">
                AND b.content like #{keyword}
            </if>
            <if test="type == 'board_member_id'">
                AND b.board_member_id like #{keyword}
            </if>
            <if test="category != null">
                AND (b.board_category_code = c.code AND c.name_eng = #{category})
            </if>
        </where>
            
        </script>
        """)
    int selectAllpage(String keyword, String type, String category);

    // 게시글 조회수 증가
    @Update("""
        UPDATE board
        SET views = views + 1
        WHERE id = #{id}
        """)
    int increaseViewCount(Integer id);
}
