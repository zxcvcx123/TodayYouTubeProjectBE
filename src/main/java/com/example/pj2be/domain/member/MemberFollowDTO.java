package com.example.pj2be.domain.member;

import lombok.Data;

import java.math.BigInteger;

@Data
public class MemberFollowDTO {
    private Integer id;  // PK
    private String follower_id;
    private String following_id;
}
