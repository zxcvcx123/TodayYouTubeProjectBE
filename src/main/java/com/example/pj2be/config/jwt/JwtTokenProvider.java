package com.example.pj2be.config.jwt;

import com.example.pj2be.domain.member.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    // properties의 secret 값을 가져와서 저장
    public JwtTokenProvider(@Value("${app.jwt.secret.key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    // 로그인 시에 토큰 값 생성
    // Member 정보를 가져와서 AccessToken, RefreshToken을 생성
    public JwtToken generateToken(Authentication authentication) {
        log.info("generateToken(토큰 생성 코드) 실행됨!!!");

        // 권한 로드
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("JwtTokenProvider generateToken의 authorities =" + authorities);
        long now = (new Date()).getTime();

        // Access 토큰 생성
        Date accessTokenExpiresIn = new Date(now + 1800000); // 테스트 용 시간
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                // 클레임명을 auth라는 이름으로 저장, 실제값: GENERAL_MEMBER
                .claim("auth",authorities)
                .setExpiration(accessTokenExpiresIn)
                // 서명 정보 HS256알고리즘
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("생성된 accessToken =" + accessToken);

        // Refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 3600000)) // 테스트 용 시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("생성된 refreshToken =" + refreshToken);
        System.out.println("생성된 jwtToken 반환" + JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
        // JwtToken 객체 설정
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Jwt 토큰을 디코딩하여 권한 정보 확인
    public Authentication getAuthentication(String accessToken){
        log.info("getAuthentication(권한정보확인) 실행됨!!!");

        // Jwt 토큰 디코딩
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null){
            throw new RuntimeException("권한이 없는 토큰입니다");
        }

        // 클레임 권한 정보 호출
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication을 리턴
        // User는 UserDetails를 구현한 class
        UserDetails principal = new User(claims.getSubject(),"",authorities);
        log.info("사용자가 가진 출처 확인", principal);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    // 토큰 정보 검증 메서드
    public boolean validateToken(String token){

        log.info("validateToken(토큰 검증) 실행됨!!!");
        try{
            // 토큰 유효성 확인
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            log.info(" 권한없는 JWT Token", e);
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT Token", e);
        }catch (UnsupportedJwtException e){
            log.info("형식이 맞지 않은 JWT Token", e);
        }catch (IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
        }
        System.out.println("valiateToken 결과는 false");
        return false;
    }

    // jwt 디코딩
    private Claims parseClaims(String accessToken){
        log.info("parseClaims(토큰 디코딩) 실행됨!!!");
        try{
            log.info("parseClaims 디코딩 과정 진행중");
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
