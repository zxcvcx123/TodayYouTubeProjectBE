package com.example.pj2be.mapper.filemapper;

import com.example.pj2be.domain.file.FileDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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

}
