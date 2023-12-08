package com.example.pj2be.mapper.membermapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProFileMapper {
    @Insert("""
        INSERT INTO memberprofileimagefile(member_id, image_name) 
        VALUES (#{member_id},#{image_name}) 
    """)
    Integer insertProfileImage(String member_id, String image_name);
}
