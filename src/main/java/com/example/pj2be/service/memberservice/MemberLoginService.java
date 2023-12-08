package com.example.pj2be.service.memberservice;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberMapper memberMapper;
    @Value("${image.file.prefix}")
    private String urlPrefix;
    public MemberDTO getLoginInfo(String member_id) {
        MemberDTO memberDTO = memberMapper.findLoginInfoByMemberId(member_id);
        try{
            String decodedFileName = memberDTO.getImage_name();
            String encodedFileName = URLEncoder.encode(decodedFileName, StandardCharsets.UTF_8.toString());
            String url = urlPrefix + "member-profiles/" + member_id+"/" + encodedFileName;
            System.out.println("url = " + url);
            memberDTO.setUrl(url);
        }catch (Exception e){

        }
        return memberDTO;
    }
}
