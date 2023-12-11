package com.example.pj2be.mapper.boardmapper;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.category.CategoryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    // 게시글 저장
    // TODO : 코드, 멤버ID 등 작성시 로그인 정보와 연동 되도록 하기.
    @Insert("""
        INSERT INTO board (title, link, content, board_category_code, board_member_id)
        VALUES (#{board.title}, 
                #{board.link}, 
                #{board.content}, 
                (SELECT code FROM category WHERE name_eng = #{category.name_eng} ),
                #{board.board_member_id}
                )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "board.id")
    void insert(BoardDTO board, CategoryDTO category);

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
               b.views,
               m.nickname,
               r.role_name
        FROM board b LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
                     LEFT JOIN comment c ON b.id = c.board_id
                          JOIN member m on b.board_member_id = m.member_id
                          JOIN roles r on m.role_id = r.role_id
                    
        WHERE b.id = #{id}
            AND b.is_show = true
        GROUP BY b.id ORDER BY b.id DESC;
        """)
    BoardDTO selectById(Integer id);

    // 게시글 리스트
    @Select("""
        <script>
        SELECT
                ROW_NUMBER() OVER (ORDER BY b.id ASC) AS rownum,
                b.id,
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
        FROM board b 
                    LEFT JOIN youtube.boardlike bl on b.id = bl.board_id
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
                 AND b.is_show = true
        </where>
        GROUP BY b.id
        ORDER BY rownum DESC
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
                 AND b.is_show = true        
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

    // 무작위 게시글 조회 용도 모든 게시판 id 가져오기
    @Select("""
            SELECT b.id, b.board_category_code, c.name, c.name_eng
            FROM board b LEFT JOIN category c
                ON b.board_category_code = c.code
            WHERE is_show = 1 AND NOT board_category_code = 'C001'
            """)
    List<Map<String, Object>> randomGet();

}
