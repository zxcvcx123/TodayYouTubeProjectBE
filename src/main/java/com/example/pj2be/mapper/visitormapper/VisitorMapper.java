package com.example.pj2be.mapper.visitormapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VisitorMapper {

    @Insert("""
        INSERT INTO visitor_statistics (member_id, ip_address) 
            VALUES (#{member_id}, #{clientIp})
        """)
    void visitorInsert(String clientIp, String member_id);

    @Select("""
        SELECT COUNT(*)
        FROM visitor_statistics;
        """)
    Integer visitorCount();
}
