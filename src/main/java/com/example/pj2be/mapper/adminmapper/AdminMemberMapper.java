package com.example.pj2be.mapper.adminmapper;

import com.example.pj2be.domain.admin.AdminMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {

    @Select("""
        SELECT COUNT(*)
        FROM member
        """)
    int selectAll();

    @Select("""
            SELECT m.id,
                   m.member_id,
                   m.nickname,
                   m.email,
                   m.phone_number,
                   m.created_at,
                   r.role_name,
                   m.gender,
                   m.birth_date
                 FROM member m
            JOIN youtube.roles r on m.role_id = r.role_id
            ORDER BY r.role_name DESC, created_at DESC
            LIMIT #{from}, 20;
            """)
    List<AdminMemberDTO> selectAllMember(Integer from);
}
