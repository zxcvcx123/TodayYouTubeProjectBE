package com.example.pj2be.service.pagingservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class PagingService {
}
