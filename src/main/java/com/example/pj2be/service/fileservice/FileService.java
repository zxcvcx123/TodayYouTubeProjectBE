package com.example.pj2be.service.fileservice;

import com.example.pj2be.domain.file.CkFileDTO;
import com.example.pj2be.domain.file.FileDTO;
import com.example.pj2be.mapper.filemapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.List;
import java.util.UUID;


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
    public void localUpload(MultipartFile[] files, Integer boardId) throws Exception {

        System.out.println("===== 파일(로컬) 업로드 시작 =====");
        // 파일경로
        File f = new File("C:\\study\\testFolder" + boardId);

        // 경로에 폴더 없으면 폴더만들기
        if (!f.exists()) {
            f.mkdirs();
        }


        for (MultipartFile file : files) {
            if (file.getSize() > 0) {

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
    public void s3Upload(MultipartFile file, Integer boardId) throws Exception {

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
        fileDTO.setFileurl(urlPrefix + "youtube/" + boardId + "/" + file.getOriginalFilename());

        System.out.println("fileDTO = " + fileDTO);

        fileMapper.upload(fileDTO);

        System.out.println("===== 파일(s3) 업로드 종료 =====");
    }

    // 게시판번호에 따른 파일 가져오기
    public List<FileDTO> getFile(Integer boardId) {

        return fileMapper.getFile(boardId);
    }

    // 테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트
    public CkFileDTO ckS3Upload(MultipartFile file) throws Exception {

        System.out.println("===== ck파일(s3) 업로드 시작 =====");
        String uuid = String.valueOf(UUID.randomUUID());

        // aws s3에 저장
        String key = "fileserver/" + uuid + "/" + file.getOriginalFilename();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)   // 버킷이름
                .key(key)         // key(파일경로)
                .acl(ObjectCannedACL.PUBLIC_READ) // 권한
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // mapper 실행전 FileDTO 세팅
        // CKEditor에서 업로드된 파일을 AWS S3에 저장한 후, 해당 파일 정보를 데이터베이스에 기록
        CkFileDTO ckfileDTO = new CkFileDTO();
        ckfileDTO.setFilename(file.getOriginalFilename());
        ckfileDTO.setCkuri(urlPrefix + "fileserver/" + uuid + "/" + file.getOriginalFilename());
        ckfileDTO.setUuid(uuid);
        System.out.println("ckfileDTO = " + ckfileDTO);

        System.out.println("===== ck파일(s3) 업로드 종료 =====");

        if (fileMapper.ckUpload(ckfileDTO) == 1) {
            return fileMapper.getCkFile(uuid);
        }

        return null;
    }

    /* 본문 ck에디터영역에 실제로 저장된 이미지의 게시판 번호 업데이트 */
    public void ckS3Update(String[] uuSrc, Integer boardId) {
        for (String src : uuSrc) {
            fileMapper.ckS3Update(src, boardId);
        }
    }

    // 임시 이미지 파일 전부 삭제 (board_id = 0 인 것)
    public void ckS3DeleteTempImg(String[] uuSrc) {
        System.out.println("===== ck임시파일(s3) 삭제 시작 =====");

        // board_id가 0인 이미지 목록 List<String>으로 담아 오기
        List<String> ckUris = fileMapper.ckS3getTempImg();

        for (String uri : ckUris) {
            String key = uri.substring(uri.indexOf("/fileserver/") + "/fileserver/".length());

//            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                    .bucket(bucket)
//                    .key(key)
//                    .build();
//
//            // S3에서 객체 삭제
//            s3.deleteObject(deleteObjectRequest);
            System.out.println("객체 삭제 됨 - key: " + key);
        }


//        fileMapper.ckS3DeleteTempImg();

        System.out.println("===== ck임시파일(s3) 삭제 완료 =====");
    }
}
