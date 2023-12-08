package com.example.pj2be.domain.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class MemberUpdateDTO {
        @NotEmpty
        private String member_id;
        private String password;
        @NotEmpty
        private String nickname;
        @NotEmpty
        private String email;
        @NotEmpty
        private String phone_number;
}
