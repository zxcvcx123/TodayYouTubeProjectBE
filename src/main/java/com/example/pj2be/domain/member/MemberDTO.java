package com.example.pj2be.domain.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


@Data
public class MemberDTO {
    private BigInteger id;  // PK
    private int role_id;    // FK
    @NotEmpty
    @Size(min = 6, max = 50)
    private String member_id;
    private String password;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone_number;
    private String gender;
    private LocalDate birth_date;
    private String role_name;
    private Integer total_like;
    private Integer total_board;
    private Integer total_comment;
    private Integer total_views;
    private Integer image_id;
    private String image_name;
    private String url;
}
