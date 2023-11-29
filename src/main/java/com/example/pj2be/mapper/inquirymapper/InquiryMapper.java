package com.example.pj2be.mapper.inquirymapper;

import com.example.pj2be.domain.inquiry.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;
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
        """)
    List<InquiryDTO> selectAll();
}
