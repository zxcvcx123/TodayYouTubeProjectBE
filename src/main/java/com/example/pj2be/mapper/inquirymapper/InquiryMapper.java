package com.example.pj2be.mapper.inquirymapper;

import com.example.pj2be.domain.inquiry.InquiryDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
                i.answer_status
         FROM inquiry i JOIN inquirycategory ic ON ic.id = i.inquiry_category
         ORDER BY i.id DESC
        """)
    List<InquiryDTO> selectAll();


    @Insert("""
        INSERT INTO inquiry (inquiry_category, title, content, inquiry_member_id)
        VALUES (
            #{inquiry_category},
            #{title},
            #{content},
            #{inquiry_member_id}
        )
        """)
    int insert(InquiryDTO dto);

    @Select("""
        SELECT i.id,
                ic.category as inquiry_category,
                i.title,
                i.content,
                i.inquiry_member_id,
                i.created_at,
                i.updated_at,
                i.answer_status
        FROM inquiry i JOIN inquirycategory ic on ic.id = i.inquiry_category
        WHERE i.id = #{id}
        """)
    InquiryDTO selectByInquiryId(Integer id);
}
