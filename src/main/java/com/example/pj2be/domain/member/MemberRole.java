package com.example.pj2be.domain.member;

import lombok.Getter;

@Getter // 상수형 자료이므로 Setter 구현 안함
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    GENERAL_MEMBER("ROLE_GENERAL_MEMBER"),
    STREAMER("ROLE_STREAMER");
MemberRole(String value){
this.value = value;
}
private String value;
}
