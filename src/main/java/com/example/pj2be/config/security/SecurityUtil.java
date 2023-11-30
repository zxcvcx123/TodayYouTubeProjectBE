package com.example.pj2be.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// GENERAL_USER를 가진 사용자에게만 허용
@Slf4j
public class SecurityUtil {
    public static String getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("getCurrentMemberId가 실행됨 -> authentication은? ", authentication.getName());
        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("정보가 없습니다.");
        }
        return authentication.getName();

    }
}
