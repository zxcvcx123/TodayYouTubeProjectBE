package com.example.pj2be.domain.board;

import lombok.Data;

import java.util.List;

@Data
public class BoardEditDTO {
    private BoardDTO board;
    private List<String> uuSrc;
}
