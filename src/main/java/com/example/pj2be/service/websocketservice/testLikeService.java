package com.example.pj2be.service.websocketservice;

import com.example.pj2be.mapper.WebSocktMapper.WebSocketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class testLikeService {

    private final WebSocketMapper mapper;

    public Map<String, Object> testLike() {



        return mapper.gettestLike(1);
    }

    public void addLike() {

        Map<String,Object> map = mapper.gettestLike(1);
        System.out.println(map);
        System.out.println(map.get("testlike"));
        Integer num = (Integer) map.get("testlike") +1;
        System.out.println(num);
        mapper.testLike(num);
    }
}
