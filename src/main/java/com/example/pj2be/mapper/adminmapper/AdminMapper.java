package com.example.pj2be.mapper.adminmapper;

import com.example.pj2be.domain.admin.BoardDataDTO;
import com.example.pj2be.domain.admin.SuspensionDTO;
import com.example.pj2be.domain.admin.UserDataDTO;
import com.example.pj2be.domain.page.PaginationDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {

    // 게시판 관련 데이터
    @Select("""
        SELECT
            c.name_eng,
            COUNT(board_category_code) AS count_category_board,
            SUM(views) AS count_category_view,
            SUM(CASE WHEN m.gender = 'm' THEN 1 ELSE 0 END) AS count_category_members_man,
            SUM(CASE WHEN m.gender = 'w' THEN 1 ELSE 0 END) AS count_category_members_woman
        FROM board b
            JOIN category c on b.board_category_code = c.code
            JOIN member m on b.board_member_id = m.member_id
        GROUP BY board_category_code
        ORDER BY board_category_code;
        """)
    List<BoardDataDTO> getBoardData();

    // 유저 게시글 작성 순위 데이터
    @Select("""
        SELECT m.member_id,
               COUNT(m.member_id) AS write_count,
               DENSE_RANK() over (ORDER BY COUNT(m.member_id) DESC) AS write_rank
        FROM member m
                 JOIN board b on m.member_id = b.board_member_id
        GROUP BY m.member_id
        ORDER BY write_rank;
        """)
    List<UserDataDTO> getUserWriteRankData();

    // 유저 좋아요 작성 순위 데이터
    @Select("""
        SELECT m.member_id,
               COUNT(m.member_id) AS like_count,
               DENSE_RANK() over (ORDER BY COUNT(m.member_id) DESC) AS like_rank
        FROM member m
                 JOIN boardlike bl on m.member_id = bl.member_id
        GROUP BY m.member_id
        ORDER BY like_rank;
        """)
    List<UserDataDTO> getUserLikeRankData();

    // 유저 댓글 작성 순위 데이터
    @Select("""
        SELECT m.member_id,
               COUNT(m.member_id) AS comment_count,
               DENSE_RANK() over (ORDER BY COUNT(m.member_id) DESC) AS comment_rank
        FROM member m
                JOIN youtube.comment c on m.member_id = c.member_id
        GROUP BY m.member_id
        ORDER BY comment_rank;
        """)
    List<UserDataDTO> getUserCommentRankData();

    @Select("""
        SELECT id,
               member_id,
               is_suspended,
               reason,
               start_date,
               end_date,
               period,
               DATEDIFF( end_date, CURRENT_TIMESTAMP) as remaindate,
               TIMEDIFF( end_date, CURRENT_TIMESTAMP) as remaintime
        FROM suspension
        WHERE suspension.end_date - current_timestamp > 0
        LIMIT #{from}, #{limit}
        """)
    List<SuspensionDTO> selectSuspensioningMember(Integer from, Integer limit);


    @Select("""
        SELECT id,
               member_id,
               is_suspended,
               reason,
               start_date,
               end_date,
               period,
               DATEDIFF( end_date, CURRENT_TIMESTAMP) as remaindate,
               TIMEDIFF( end_date, CURRENT_TIMESTAMP) as remaintime
        FROM suspension
        WHERE suspension.end_date - current_timestamp < 0;
        """)
    List<SuspensionDTO> selectReleaseMember();

    @Delete("""
        DELETE FROM suspension
        WHERE id = #{id}
        """)
    void deleteSuspension(Integer id);

    @Select("""
        SELECT id,
               member_id,
               is_suspended,
               reason,
               DATE_FORMAT(start_date, '%Y-%m-%d') as start_date_only,
               DATE_FORMAT(end_date, '%Y-%m-%d') as end_date_only,
               period,
               DATEDIFF( end_date, CURRENT_TIMESTAMP) as remaindate,
               TIMEDIFF( end_date, CURRENT_TIMESTAMP) as remaintime
        FROM suspension
        WHERE suspension.member_id = #{memberId};
        """)
    SuspensionDTO selectSuspensionMember(String memberId);

    @Select("""
        SELECT COUNT(id)
        FROM suspension
        WHERE suspension.end_date - current_timestamp > 0;
        """)
    int selectAllMember();

}
