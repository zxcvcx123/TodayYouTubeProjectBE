package com.example.pj2be.domain;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class MemberDTO {
    private BigInteger id;
    private String member_id;
    private String password;
    private String nickname;
    private String email;
    private String phone_number;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
