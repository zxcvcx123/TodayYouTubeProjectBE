package com.example.pj2be.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginDTO {
    @NotEmpty
    private String member_id;
    @NotEmpty
    private String password;

}
