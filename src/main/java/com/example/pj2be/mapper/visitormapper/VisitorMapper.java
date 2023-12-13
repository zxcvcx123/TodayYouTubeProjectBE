package com.example.pj2be.mapper.visitormapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VisitorMapper {

    /**
     * 방문자 기록하기. 사용자 ID, 클라이언트 IP
     */
    @Insert("""
        INSERT INTO visitor_statistics (member_id, ip_address) 
            VALUES (#{member_id}, #{clientIp})
        """)
    void visitorInsert(String clientIp, String member_id);

    /**
    * 방문자 기록 시 30분 전 이력 있다면 제외 <br>
     * 30분 전 이력 카운트 해서 0이면 통과
     * @return Integer
    * */
    @Select("""
        SELECT COUNT(*)
        FROM visitor_statistics
        WHERE member_id = #{memberId}
            AND inserted_at > NOW() - INTERVAL 30 MINUTE ;
        """)
    Integer countRecentVisit(String memberId);

    /**
     * 전체 방문자 수 집계
     * @return Integer
     */
    @Select("""
        SELECT COUNT(*)
        FROM visitor_statistics;
        """)
    Integer visitorCountAll();

    /**
     * 오늘의 방문자 수 집계 (중복 제거)
     * @return Integer
     */
    @Select("""
        SELECT COUNT(DISTINCT member_id) AS today_visitor
        FROM visitor_statistics
        WHERE DATE(inserted_at) = CURDATE();
        """)
    Integer visitorCountToday();

    @Select("""
        SELECT
            CONCAT(YEAR(inserted_at), '-', LPAD(MONTH(inserted_at), 2, '0')) AS `year_month`,
            COUNT(member_id) AS unique_visitors
        FROM visitor_statistics
        WHERE inserted_at >= CURDATE() - INTERVAL 12 MONTH
        GROUP BY YEAR(inserted_at), MONTH(inserted_at)
        ORDER BY `year_month` DESC;
        """)
    void visitorCountMonthlyLastYear();
}
