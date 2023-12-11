package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.member.MemberProfileImageDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProFileMapper {
    @Insert("""
        INSERT INTO memberprofileimagefile(member_id, image_name) 
        VALUES (#{member_id},#{image_name}) 
    """)
    Integer insertProfileImage(String member_id, String image_name);

    @Select("""
            SELECT image_name, id FROM memberprofileimagefile WHERE member_id = #{member_id}
            """)
    MemberProfileImageDTO selectProfileByMemberId(String member_id);

    @Delete("""
            DELETE FROM memberprofileimagefile
            WHERE member_id = #{member_id}
            """)
    int deleteProfileByMemberId(String member_id);
}
