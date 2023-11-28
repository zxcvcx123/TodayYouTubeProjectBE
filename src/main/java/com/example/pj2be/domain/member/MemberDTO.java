package com.example.pj2be.domain.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;


@Data
public class MemberDTO {
    private BigInteger id;  // PK
    private int role_id;    // FK
    @NotEmpty
    @Size(min = 6, max = 50)
    private String member_id;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone_number;
    private String gender;
    private LocalDate birth_date;

}
