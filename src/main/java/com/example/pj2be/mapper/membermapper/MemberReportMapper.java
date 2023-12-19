package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.member.ReportDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberReportMapper {
    @Insert("""
            INSERT INTO member_report (reporter_id, reported_id, report_reason, board_id) 
            VALUES (#{reporterId},#{reportedId},#{reportReason}, #{boardId})
            """)
    boolean reportMemberByMemberId(String reporterId, String reportedId, String reportReason, int boardId);


    @Select("""
            SELECT COUNT(*) FROM member_report WHERE reporter_id = #{reporterId} AND reported_id = #{reportedId} AND board_id = #{boardId}
            """)
    int isMemberReported(String reporterId, String reportedId, Integer boardId);

    @Select("""
            <script>
            SELECT COUNT(DISTINCT board_id) FROM member_report
                        <trim prefix="WHERE" >
                            <if test="reportCategory == 'unResolve'">
                                 is_resolved = 0;
                            </if>\s            
                            <if test="reportCategory == 'resolved'">
                                 is_resolved = 1;
                            </if>\s
                        </trim>
                        </script>
            """)
    int countAll(String reportCategory);
    @Select("""
            <script>
                        SELECT row_number() OVER (ORDER BY rp.reported_id) AS row_num, rp.id , rp.board_id, rp.reported_id, COUNT(DISTINCT rp.id) AS countReported, r.role_name, m.nickname
                        FROM member_report rp
                        LEFT JOIN board b ON rp.board_id = b.id 
                        LEFT JOIN category c ON b.board_category_code = c.code
                        LEFT JOIN member m ON rp.reported_id = m.member_id
                        LEFT JOIN roles r ON r.role_id = m.role_id
                        <trim prefix="WHERE" >
                            <if test="reportCategory == 'unResolve'">
                                 is_resolved = 0
                            </if>\s            
                            <if test="reportCategory == 'resolved'">
                                 is_resolved = 1
                            </if>\s
                        </trim>            
                        GROUP BY rp.reported_id
                        ORDER BY COUNT(DISTINCT rp.id) DESC
                        LIMIT #{from}, 15;
                                
                  </script>
                      
                                   """)
    List<ReportDTO> getReportList(String reportCategory, int from);

    @Select("""
SELECT mr.id,  mr.reporter_id, mr.reported_id, mr.report_reason, mr.report_date, r.role_name, c.name as category_name, mr.board_id
FROM member_report mr
     LEFT JOIN member m
        ON mr.reporter_id = m.member_id
    LEFT JOIN roles r
        ON m.role_id = r.role_id
     LEFT JOIN  board b
        ON mr.board_id = b.id
     LEFT JOIN category c
        ON b.board_category_code = c.code
WHERE mr.reported_id = #{reported_id}
ORDER BY c.id
            """)
    List<ReportDTO> getAllReportedList(String reported_id);

    @Update("""
            UPDATE member_report
            SET is_resolved = 1
            WHERE reported_id = #{reportedId}
            """)
    boolean updateResolved(String reportedId);

    @Update("""
            UPDATE board
            SET is_show = 0
            WHERE board_member_id = #{reportedId}
            """)
    boolean changeIsShow(String reportedId);

    @Delete("""
            DELETE FROM member_report
            WHERE reported_id = #{reportedId}
            """)
    boolean rejectReport(String reportedId);
}
