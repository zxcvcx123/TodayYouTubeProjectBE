package com.example.pj2be.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;   // 토큰 유형
    private String accessToken; // 인증 토큰
    private String refreshToken;    // 인증 토큰 재발급을 위한 토큰
}
