package com.example.pj2be.mapper.filemapper;

import com.example.pj2be.domain.file.CkFileDTO;
import com.example.pj2be.domain.file.FileDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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
}
