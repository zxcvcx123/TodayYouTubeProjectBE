package com.example.pj2be.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 클라이언트 요청 시 JWT 인증을 위함
// 토큰 유효성 검증 및 건증 후 해당 토큰의 인증 정보를 SecurityContext에 저장하여 인증된 요청을 처리
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // jwt 토큰 추출 및 유효성 검증
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            HttpServletRequest req = (HttpServletRequest) request;
            String path = req.getServletPath();
            if(path.contains("/api/member")){
                System.out.println("(JwtAuthenticationFilter: doFilter)유효성 검증 시작 ==========================================");
                Map<String, Object> token = getJwtFromCookie(req);
                String access = (String) token.get("jwtAccess");
                String refresh = (String) token.get("jwtRefresh");
                String accessToken = resolveToken(access);
                String refreshToken = resolveToken(refresh);

                // 토큰이 존재하는 경우 권한 검사
                if(token != null && jwtTokenProvider.validateToken(access)){
                    Authentication authentication = jwtTokenProvider.getAuthentication(access);
                    // SecurtiyContext에 인증 정보 설정
                    System.out.println("(dofilter)권한은? = " + authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                System.out.println( path + " = 유효성 검증 끝 ================================");
            }
        }catch (Exception e){
                System.out.println(" (JwtAuthenticationFilter: doFilter) 예외 발생");
//                System.out.println("(JwtAuthenticationFilter: doFilter) 유효성 검증 끝 ================================");
        }
        // 로그인 요청 처리 후, filter 체인에게 전달

        chain.doFilter(request, response);
    }

    // 요청 헤더에서 토큰 정보 추출
    private String resolveToken(String token){;
        if(StringUtils.hasText(token) && token.startsWith("Bearer")){
            return token.substring(7);
        }
        return null;
    }
    private Map<String, Object> getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Object> jwt = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtAccess".equals(cookie.getName())) {
                     jwt.put("jwtAccess",cookie.getValue());
                }
                if("jwtRefresh".equals(cookie.getName())){
                    jwt.put("jwtRefresh", cookie.getValue());
                }
            }
        }
        return null;
    }

}
