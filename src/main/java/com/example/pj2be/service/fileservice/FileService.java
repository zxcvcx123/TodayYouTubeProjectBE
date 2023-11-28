package com.example.pj2be.service.fileservice;

import com.example.pj2be.domain.file.FileDTO;
import com.example.pj2be.mapper.filemapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class FileService {

    private final FileMapper fileMapper;

    private final S3Client s3;

    @Value("mybucketcontainer1133557799")
    private String bucket;

    @Value("https://mybucketcontainer1133557799.s3.ap-northeast-2.amazonaws.com/")
    private String urlPrefix;



    // 로컬에 저장 테스트 로직
    public void localUpload(MultipartFile[] files, Integer boardId) throws Exception{

        System.out.println("===== 파일(로컬) 업로드 시작 =====");
        // 파일경로
        File f = new File("C:\\study\\testFolder" + boardId);

        // 경로에 폴더 없으면 폴더만들기
        if(!f.exists()){
            f.mkdirs();
        }


        for (MultipartFile file : files){
            if(file.getSize() > 0){

                String path = f.getAbsolutePath() + "\\" + file.getOriginalFilename();
                File des = new File(path);
                file.transferTo(des);

                // mapper 실행전 FileDTO 세팅
                FileDTO fileDTO = new FileDTO();
                fileDTO.setBoard_id(2);

                fileDTO.setFilename(file.getOriginalFilename());

                // mapper 실행
                fileMapper.upload(fileDTO);
            }
        }

        System.out.println("===== 파일(로컬) 업로드 종료 =====");

    }

    // s3 파일 업로드
    public void s3Upload(MultipartFile file, Integer boardId) throws Exception{

        System.out.println("===== 파일(s3) 업로드 시작 =====");

        // aws s3에 저장
        String key = "youtube/" + boardId + "/" + file.getOriginalFilename();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)   // 버킷이름
                .key(key)         // key(파일경로)
                .acl(ObjectCannedACL.PUBLIC_READ) // 권한
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // mapper 실행전 FileDTO 세팅
        FileDTO fileDTO = new FileDTO();
        fileDTO.setBoard_id(boardId);
        fileDTO.setFilename(file.getOriginalFilename());
        fileDTO.setFileurl(urlPrefix + "youtube/" + boardId +"/"+file.getOriginalFilename());

        System.out.println("fileDTO = " + fileDTO);

        fileMapper.upload(fileDTO);

        System.out.println("===== 파일(s3) 업로드 종료 =====");
    }

    // 게시판번호에 따른 파일 가져오기
    public List<FileDTO> getFile(Integer boardId){

        return fileMapper.getFile(boardId);
    }
}
