package com.example.pj2be.config.security;

import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.member.MemberRole;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 사용자를 조회하는 서비스( 로그인 처리)
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {
// TODO: MemberSecurityService 다 작성 후, 스프링 시큐리티에 등록해야함.

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {

        log.info("loadUserByUsername 실행됨.");
        Optional<MemberDTO> _member = memberMapper.findByMemberId(member_id);
        if(_member.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }

        MemberDTO member = _member.get();
        System.out.println("로그인 시도한 사용자 = " + member);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (member.getRole_id() == 1) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
            System.out.println("loadUserByUsername 실행됨 -> 시용자 권한 등록증 : " + authorities);
        }else if(member.getRole_id() == 2) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.GENERAL_MEMBER.getValue()));
            System.out.println("loadUserByUsername 실행됨 -> 권한 등록 : "+ authorities);
        }

        User userInfo = new User(member.getMember_id(), member.getPassword(), authorities);
        System.out.println("loadUserByUsername 검증 완료 : 유저의 정보 = " + userInfo);

return userInfo;
    }
}
