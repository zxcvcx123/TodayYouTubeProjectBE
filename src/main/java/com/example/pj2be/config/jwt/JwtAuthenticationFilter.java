package com.example.pj2be.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// 클라이언트 요청 시 JWT 인증을 위함
// 토큰 유효성 검증 및 건증 후 해당 토큰의 인증 정보를 SecurityContext에 저장하여 인증된 요청을 처리
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // jwt 토큰 추출 및 유효성 검증
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter가 실행됨!!");
        String token = resolveToken((HttpServletRequest) request);
        System.out.println("doFilter가 실행됨!! token = " + token);

        // 토큰 유효성 검사
        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("JwtAuthenticationFilter dofilter의 if문 실행");
        }

        // 로그인 요청 처리 후, Security Config의 filter 체인에게 전달
        chain.doFilter(request, response);
        System.out.println("doFilter로 돌아옴 chain.dofilter(request ="+request+", response "+response);
    }

    // 요청 헤더에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request){
        log.info("resolveToken 실행됨!!");
        log.info(request.getRequestURI());
        String bearerToken = request.getHeader("Authorization");
        System.out.println("resolveToken의 bearerToken = " + bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            System.out.println("resolveToken의 반환값 " + bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}
