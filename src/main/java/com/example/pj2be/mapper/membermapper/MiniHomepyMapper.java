package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.YoutuberInfoDTO;
import com.example.pj2be.domain.minihomepy.MiniHomepyDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface MiniHomepyMapper {

    @Select("""
            SELECT homepy_id, member_id, title, introduce, bgm_link,
            (SELECT COUNT(login_member_id) 
            FROM mini_homepy_visitor 
            WHERE DATE(visit_date) = CURDATE() 
            AND member_id = #{memberId}) AS today_visitors,
            (SELECT COUNT(login_member_id) 
            FROM mini_homepy_visitor 
            WHERE member_id = #{memberId}) AS total_visitors
            FROM mini_homepy
             WHERE member_id = #{memberId}
            """)
    MiniHomepyDTO getInfo(String memberId);

    @Update("""
            UPDATE mini_homepy
            SET introduce = #{introduce}
            WHERE member_id = #{member_id}
            """)
    Boolean updateIntroduceByMemberId(String member_id, String introduce);

    @Insert("""
            INSERT INTO mini_homepy (member_id)
            VALUES (#{memberId})
            """)
    Boolean createUserHomepy(String memberId);

    @Insert("""
            INSERT INTO mini_homepy_visitor (member_id, login_member_id) 
            VALUES (#{member_id}, #{login_member_id})
            """)
    Boolean addHomepyVisiterViewByLoginMemberId(String member_id, String login_member_id);

    @Select("""
            SELECT COUNT(*) > 0 FROM mini_homepy_visitor
            WHERE member_id = #{member_id}
            AND login_member_id = #{login_member_id}
            AND DATE(visit_date) = CURDATE();
            """)
    boolean checkLoginMemberIdExistsInCurrentDate(String member_id, String login_member_id);

    @Update("""
            UPDATE mini_homepy
            SET bgm_link = #{bgm_link}
            WHERE member_id = #{member_id}
            """)
    boolean updateBgmByMemberId(String member_id, String bgm_link);

    @Select("""
                        SELECT
                           b.id,
                           b.title,
                           b.link,
                           m.nickname,
                           b.updated_at,
                           b.views,
                           COUNT(DISTINCT l.id) AS countlike
                        FROM board b
                        JOIN member m ON b.board_member_id = m.member_id
                        LEFT JOIN boardlike l on b.id = l.board_id
                        WHERE b.board_member_id = #{memberId}  
                       GROUP BY b.id
                       HAVING  b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                       ORDER BY COUNT(l.id) DESC 
                        LIMIT 0, 10;
            """)
    List<BoardDTO> getTopBoardList(String memberId);

    @Select("""
                        SELECT
                           b.id,
                           b.title,
                           b.link,
                           m.nickname,
                           b.updated_at,
                           b.views,
                           COUNT(DISTINCT l.id) AS countlike
                        FROM board b
                        JOIN member m ON b.board_member_id = m.member_id
                        LEFT JOIN boardlike l on b.id = l.board_id
                        WHERE b.board_member_id = #{memberId}  
                       GROUP BY b.id
                       HAVING  b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                       ORDER BY b.updated_at DESC 
                        LIMIT 0, 10;
            """)
    List<BoardDTO> getNewBoardList(String memberId);

    @Select("""
SELECT
    b.id,
    b.title,
    b.link,
    m.nickname,
    b.updated_at,
    b.views,
    COUNT(DISTINCT l.id) AS countlike
FROM board b
         JOIN member m ON b.board_member_id = m.member_id
         LEFT JOIN boardlike l on b.id = l.board_id
WHERE l.member_id = #{memberId}
GROUP BY b.id
HAVING  b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                        LIMIT 0, 10;
            """)
    List<BoardDTO> getFavoriteBoardList(String memberId);
    @Select("""
            <script>
                        SELECT
                           b.id,
                           b.title,
                           b.link,
                           m.nickname,
                           b.updated_at,
                           b.views,
                           COUNT(DISTINCT l.id) AS countlike
                        FROM board b
                        JOIN member m ON b.board_member_id = m.member_id
                        LEFT JOIN boardlike l on b.id = l.board_id
                        WHERE b.board_member_id = #{memberId}
                        <if test="searchingKeyword != null and searchingKeyword != ''">
                        AND b.title LIKE #{searchingKeyword}
                        </if>
                       GROUP BY b.id
                       HAVING  b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                            <trim prefix="ORDER BY">
                                      <if test="categoryOrdedBy == 'latest'">
                                           b.created_at DESC
                                      </if>\s
                                      <if test="categoryOrdedBy == 'like'">
                                           COUNT(l.id) DESC
                                      </if>\s
                                      <if test= "categoryOrdedBy == 'views'">
                                          b.views DESC
                                      </if>\s
                                  </trim>
                  </script>
                      
                                   """)
    List<BoardDTO> getAllBoardList(String memberId, String categoryOrdedBy, String searchingKeyword);

    @Insert("""
    INSERT INTO youtuberInfo (member_id, title, customUrl, publishedAt, thumbnails, description, videoCount, subscriberCount, viewCount, country)
    VALUES (#{memberId}, 
    #{title}, 
    #{customUrl}, 
    #{publishedAt}, 
    #{thumbnails}, 
    #{description}, 
    #{videoCount}, 
    #{subscriberCount}, 
    #{viewCount}, 
    #{country})
""")
    int addYoutuberInfoByMemberId(String memberId, String title, String customUrl, LocalDateTime publishedAt, String thumbnails, String description, String videoCount, String subscriberCount, String viewCount, String country);

    @Select("""
            SELECT * FROM youtuberinfo WHERE member_id = #{memberId} ORDER BY id DESC;
            """)
    List<YoutuberInfoDTO> getYoutuberInfoList(String memberId);

    @Insert("""
            INSERT INTO mini_homepy_comment (member_id, comment, image_url, homepy_id) 
            VALUES (#{memberId},
           #{comment},
           #{imageUrl}, #{homepyId})
           
            """)
    boolean addMiniHomepyCommentById(String memberId, String comment, String imageUrl, int homepyId);

    @Select("""
            """)
    Map<String, Object> getMiniHomepyCommentByHomepyId(int homepyId);
}
