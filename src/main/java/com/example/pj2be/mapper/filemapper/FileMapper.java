package com.example.pj2be.mapper.filemapper;

import com.example.pj2be.domain.file.CkFileDTO;
import com.example.pj2be.domain.file.FileDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("""
            INSERT INTO file (
                board_id, filename, fileurl
            ) VALUES (
            #{board_id}, #{filename}, #{fileurl}
            )
            """)
    void upload(FileDTO file);

    @Select("""
            SELECT * FROM file
            WHERE board_id = #{boardId}
            """)
    List<FileDTO> getFile(Integer boardId);

    @Select("""
            SELECT MAX(id) FROM board
            """)
    Integer getLastBoardId();

    @Insert("""
            INSERT INTO ck_s3 (uuid, filename, ckuri) 
            VALUES (#{uuid}, #{filename}, #{ckuri})
            """)
    int ckUpload(CkFileDTO ckfileDTO);

    @Select("""
            SELECT * FROM ck_s3
            WHERE uuid = #{uuid}
            """)
    CkFileDTO getCkFile(String uuid);


    /* 본문 ck에디터영역에 실제로 저장된 이미지의 게시판 번호 업데이트 (임시저장 기본값 : 0) */
    @Update("""
            UPDATE ck_s3 
            SET board_id = #{boardId}
            WHERE uuid = #{src}
            """)
    void ckS3Update(String src, Integer boardId);
}
