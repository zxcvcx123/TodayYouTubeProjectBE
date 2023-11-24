package com.example.pj2be.domain;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberDTO {
    private BigInteger id;  // PK
    private String role_id; // FK
    private String member_id;
    private String gender;
    private String password;
    private String nickname;
    private String email;
    private String phone_number;
    private LocalDate birth_date;

}
