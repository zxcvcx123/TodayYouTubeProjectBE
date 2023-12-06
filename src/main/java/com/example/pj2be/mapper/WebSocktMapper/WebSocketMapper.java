package com.example.pj2be.mapper.WebSocktMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface WebSocketMapper {



    @Update("""
            UPDATE socket
            SET testlike = #{num}
            WHERE id = 1
            """)
    void testLike(Integer num);

    @Select("""
            SELECT testlike FROM socket
            WHERE id = #{i}
            """)
    Map<String, Object> gettestLike(Integer i);
}
