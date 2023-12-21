package com.example.pj2be.service.memberservice;

import com.example.pj2be.config.s3client.AwsConfig;
import com.example.pj2be.domain.member.*;
import com.example.pj2be.mapper.membermapper.ProFileMapper;
import com.example.pj2be.mapper.membermapper.MemberInfoMapper;
import com.example.pj2be.mapper.membermapper.MemberMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberInfoService {

    private final MemberInfoMapper memberInfoMapper;
    private final MemberMapper memberMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final ProFileMapper proFileMapper;
    @Value("${aws.s3.bucket.name}")
    private String awsBucket;
    private final S3Client s3;
    @Value("${image.file.prefix}")
    private String urlPrefix;
    public Map<String, Object> getMyBoardList(String member_id, String categoryOrdedBy, String categoryTopics, Integer page) {

        Map<String, Object> myBoardListMap = new HashMap<>();
        Map<String, Object> pagingInformation = new HashMap<>();
        int countAll = memberInfoMapper.countAll(member_id, categoryTopics); // 기준 별 글 수

        int lastPageNumber = (countAll-1)/ 10 +1; // 마지막 페이지 번호 (예: 14 -> 2)

        int startPageNumber = (page -1)/10*10 +1;   // 페이지 그룹의 시작 번호
        int endPageNumber = startPageNumber + 9;    // 페이지 그룹의 마지막 번호
        endPageNumber= Math.min(endPageNumber, lastPageNumber); // 가장 끝 페이지 그룹에서 마지막 번호를 나타내기 위함
        pagingInformation.put("currentPageNumber", page);   // 현재 위치한 페이지
        pagingInformation.put("startPageNumber", startPageNumber);
        pagingInformation.put("endPageNumber", endPageNumber);
        pagingInformation.put("lastPageNumber", lastPageNumber);
        int prevPageNumber = startPageNumber -10;   // 페이지 그룹의 이전
        int nextPageNumber = endPageNumber +1;  // 페이지 그룹의 이후
        if(prevPageNumber > 0){ // 현재 페이지 그룹이 1~10인 경우 0이 되는 것을 방지
            pagingInformation.put("prevPageNumber", startPageNumber -10);
        }
        if(nextPageNumber< lastPageNumber){ // 가장 마지막 페이지보다 페이지 그룹 끝이 작을 때만 표시
            pagingInformation.put("nextPageNumber", nextPageNumber);
        }
        int from = (page -1) * 10;
        myBoardListMap.put("myBoardList", memberInfoMapper.getMyBoardList(member_id, categoryOrdedBy, categoryTopics, from));
        myBoardListMap.put("pagingInformation", pagingInformation);
        System.out.println("실행여부 확인 Service");
        return myBoardListMap;
    }

    public boolean validateMemberPassword(String memberId, String password) throws UsernameNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication validateAuthentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return validateAuthentication.isAuthenticated();
    }

    public boolean updateMemberInformation(@Valid MemberUpdateDTO memberUpdateDTO) {

        if(memberUpdateDTO.getPassword().length() >= 8){
            String member_id = memberUpdateDTO.getMember_id();
            String nickname = memberUpdateDTO.getNickname();
            String email = memberUpdateDTO.getEmail();
            String phoneNumber = memberUpdateDTO.getPhone_number();
            if(memberUpdateDTO.getPassword().equals(getPasswordByMemberId(memberUpdateDTO.getMember_id()))){
                String password = memberUpdateDTO.getPassword();
                return memberInfoMapper.updateMemberInformation(member_id, nickname, email, password);
            }else {
                if (validateMemberPassword(memberUpdateDTO.getMember_id(), memberUpdateDTO.getPassword())) {
                    memberUpdateDTO.setPassword(passwordEncoder.encode(memberUpdateDTO.getPassword()));
                    String password = memberUpdateDTO.getPassword();
                    return memberInfoMapper.updateMemberInformation(member_id, nickname, email, password);
                }
            }
        }
        return false;
    }

    public String getPasswordByMemberId(String member_id) throws EmptyResultDataAccessException {
        String password = memberInfoMapper.getPasswordByMemberId(member_id);
        if(password != null){
            return password;
        }else {
            throw new EmptyResultDataAccessException(1);
        }
    }

    public Boolean updateProfileImage(MemberLoginDTO memberDTO, MultipartFile files) throws IOException {
        System.out.println("service updateProfileImage");
        if(files != null){
            System.out.println("updateProfileImage + IF문 안");
            // 기존에 있던 사진 지우기
            String member_id = memberDTO.getMember_id();
            MemberProfileImageDTO memberProfileImageDTO = proFileMapper.selectProfileByMemberId(member_id);
            if(memberProfileImageDTO != null) {
                String imageName = memberProfileImageDTO.getImage_name();
                Integer id = memberProfileImageDTO.getId();
                if (imageName != null && id != null) {
                    String key = "member-profiles/" + member_id + "/" + imageName;

                    DeleteObjectRequest objectRequest = DeleteObjectRequest.builder()
                            .bucket(awsBucket)
                            .key(key)
                            .build();

                    s3.deleteObject(objectRequest);
                    proFileMapper.deleteProfileByMemberId(member_id);
                }
            }
            proFileMapper.insertProfileImage(memberDTO.getMember_id(), files.getOriginalFilename());  // DB에 파일저장
                uploadProfileImageToAws(memberDTO.getMember_id(), files);
            return true;
        }
        return false;
    }

    private void uploadProfileImageToAws(String member_id, MultipartFile file)throws IOException {
        System.out.println("member_id = " + member_id);
        String key = "member-profiles/"+member_id+"/" + file.getOriginalFilename();
        System.out.println("key = " + key);
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(awsBucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    public String searchMember(String nickname) {
        return memberInfoMapper.searchMemberByMemberId(nickname);
    }

    public boolean addFollowing(MemberFollowDTO memberFollowDTO) {
        String follower = memberFollowDTO.getFollower_id();
        String following = memberFollowDTO.getFollowing_id();
        if(memberInfoMapper.isFollowing(follower, following) != 1){
            return memberInfoMapper.addFollowing(follower, following);
        }
            return false;
    }

    public Map<String, Object> getFollowList(String memberId) throws UnsupportedEncodingException {
        Map<String, Object> getFollowList = new HashMap<>();
        List<MemberDTO> followingList = memberInfoMapper.getFollowingListByMemberId(memberId);
        for (MemberDTO memberDTO: followingList) {
            if(memberDTO.getImage_name() != null) {
                String decodedFileName = memberDTO.getImage_name();
                String encodedFileName = URLEncoder.encode(decodedFileName, StandardCharsets.UTF_8.toString());
                String url = urlPrefix + "member-profiles/" + memberDTO.getMember_id() + "/" + encodedFileName;
                memberDTO.setUrl(url);
            }
        }

        List<MemberDTO> followerList = memberInfoMapper.getFollowerListByMemberId(memberId);
        for (MemberDTO memberDTO: followingList) {
            if(memberDTO.getImage_name() != null) {
                String decodedFileName = memberDTO.getImage_name();
                String encodedFileName = URLEncoder.encode(decodedFileName, StandardCharsets.UTF_8.toString());
                String url = urlPrefix + "member-profiles/" + memberDTO.getMember_id() + "/" + encodedFileName;
                memberDTO.setUrl(url);
            }
        }

        getFollowList.put("followingList", followingList);
        getFollowList.put("followerList", followerList);

        return getFollowList;
    }

    public boolean deleteFollowing(String followingId, String followerId) {

        return memberInfoMapper.deleteFollowing(followingId, followerId);
    }

    public boolean isMemberFollowing(String loginId, String memberId) {
        System.out.println("memberInfoMapper.isFollowing(loginId, memberId) != 1 => "+memberInfoMapper.isFollowing(loginId, memberId));
        return memberInfoMapper.isFollowing(loginId, memberId) != 1;
    }
}
