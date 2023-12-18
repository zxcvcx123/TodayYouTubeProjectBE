package com.example.pj2be.domain.admin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminMemberDTO {
    private Integer id;
    private String member_id;
    private String email;
    private String nickname;
    private String phone_number;
    private String created_at;
    private String role_name;
    private String gender;
    private LocalDate birth_date;
    private Integer countlike;
    private Integer countboard;
    private Integer countcomment;
    private Integer countcommentreply;
    private Boolean is_suspended;

}
