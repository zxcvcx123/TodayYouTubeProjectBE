package com.example.pj2be.mapper.inquirymapper;

import com.example.pj2be.domain.answer.AnswerDTO;
import com.example.pj2be.domain.inquiry.InquiryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface InquiryMapper {

    @Select("""
        SELECT i.id,
                ic.category as inquiry_category,
                i.title,
                i.content,
                i.inquiry_member_id,
                i.created_at,
                i.updated_at,
                i.answer_status,
                a.content answerContent
         FROM inquiry i JOIN inquirycategory ic ON ic.id = i.inquiry_category
         LEFT JOIN youtube.answer a on i.id = a.answer_board_id
         WHERE i.inquiry_category NOT LIKE '5'
         ORDER BY i.id DESC
         LIMIT #{from}, 10
        """)
    List<InquiryDTO> selectAll(Integer from);


    @Insert("""
        INSERT INTO inquiry (inquiry_category, title, content, inquiry_member_id)
        VALUES (
            #{inquiry_category},
            #{title},
            #{content},
            #{inquiry_member_id}
        )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InquiryDTO dto);

    @Select("""
        SELECT i.id,
                ic.category as inquiry_category,
                i.title,
                i.content,
                i.inquiry_member_id,
                i.created_at,
                i.updated_at,
                i.answer_status,
                a.content answerContent,
                i.inquiry_category as category_code
        FROM inquiry i JOIN inquirycategory ic on ic.id = i.inquiry_category
         LEFT JOIN youtube.answer a on i.id = a.answer_board_id
        WHERE i.id = #{id}
        ORDER BY i.id DESC;
        """)
    InquiryDTO selectByInquiryId(Integer id);


    @Delete("""
        DELETE FROM inquiry
        WHERE id = #{id}
        """)
    int deleteByInquiryId(Integer id);


    @Update("""
        UPDATE inquiry
        SET title = #{title},
            content = #{content},
            inquiry_category = #{inquiry_category}
        WHERE id = #{id}
        """)
    int update(InquiryDTO dto);

    @Insert("""
        INSERT INTO answer (answer_board_id, title, content)
        VALUES (
            #{answer_board_id},
            '답변이 완료되었습니다.',
            #{content}
        )
        """)
    int insertAnswer(AnswerDTO dto);

    @Update("""
        UPDATE inquiry
        SET answer_status = #{answer_status}
        WHERE id = #{id}
        """)
    int updateAnswerState(InquiryDTO inquiryDTO);

    @Select("""
        SELECT COUNT(*)
        FROM inquiry
        WHERE inquiry_category NOT LIKE '5';
        """)
    int selectAllpage();

    @Select("""
        SELECT COUNT(*)
        FROM inquiry
        WHERE inquiry_member_id = #{loginMember}
        """)
    int selectPageByMemberId(String loginMember);

    @Select("""
        
            SELECT i.id,
               ic.category as inquiry_category,
               i.title,
               i.content,
               i.inquiry_member_id,
               i.created_at,
               i.updated_at,
               i.answer_status,
               a.content answerContent
        FROM inquiry i JOIN inquirycategory ic ON ic.id = i.inquiry_category
                       LEFT JOIN youtube.answer a on i.id = a.answer_board_id
        WHERE inquiry_member_id = #{id} AND ic.category NOT LIKE '유저신고'
        ORDER BY i.id DESC
        LIMIT #{from}, 10;
        """)
    List<InquiryDTO> selectByMemberId(String id, Integer from);


    @Update("""
        UPDATE answer
        SET content = #{content}
        WHERE answer_board_id = #{answer_board_id}
        """)
    int editAnswer(AnswerDTO dto);


    @Delete("""
        DELETE FROM answer
        WHERE answer_board_id = #{answer_board_id}
        """)
    int deleteAnswer(AnswerDTO dto);


    @Select("""
        SELECT i.id,
               ic.category as inquiry_category,
               i.title,
               i.content,
               i.inquiry_member_id,
               i.created_at,
               i.updated_at,
               i.answer_status
        FROM inquiry i JOIN inquirycategory ic ON ic.id = i.inquiry_category
        WHERE inquiry_category = 5;
        """)
    List<InquiryDTO> selectInquiryNotice();
}
