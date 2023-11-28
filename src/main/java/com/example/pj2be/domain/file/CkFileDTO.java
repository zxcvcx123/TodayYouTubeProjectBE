package com.example.pj2be.domain.file;

import lombok.Data;

@Data
public class CkFileDTO {
    private Integer id;
    private String uuid;
    private String filename;
    private String ckuri;
}
