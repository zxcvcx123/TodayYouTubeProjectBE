package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberDTO;
import com.example.pj2be.domain.member.MemberFollowDTO;
import com.example.pj2be.domain.member.MemberUpdateDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberInfoMapper {

    @Select("""
            <script>
                        SELECT
                           b.id,
                           b.title,
                           b.link,
                           m.nickname,
                           b.created_at,
                           b.views,
                           COUNT(DISTINCT l.id) AS countlike
                        FROM board b
                        JOIN member m ON b.board_member_id = m.member_id
                        LEFT JOIN boardlike l on b.id = l.board_id
                        WHERE b.board_member_id = #{member_id}  
                       GROUP BY b.id
                            <trim prefix="HAVING">
                                      <if test="categoryTopics == 'video'">
                                           b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                                      </if>\s
                                      <if test= "categoryTopics == 'origin'">
                                          b.link not like 'https://%' AND b.link not like '%youtu%'
                                      </if>\s
                            </trim>
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
                                  LIMIT #{from}, 10;
                  </script>
                      
                                   """)
    List<BoardDTO> getMyBoardList(String member_id, String categoryOrdedBy, String categoryTopics, Integer from );

    @Select("""
            <script>
            SELECT COUNT(*) FROM board b
                JOIN member m ON b.board_member_id = m.member_id      
                        <trim prefix="WHERE" prefixOverrides="AND">
                                    <if test="member_id != null">
                                          b.board_member_id = #{member_id}  
                                      </if>\s
                                      <if test="categoryTopics == 'video'">
                                          AND b.link LIKE 'https://%' AND b.link LIKE '%youtu%'
                                      </if>\s
                                      <if test= "categoryTopics == 'origin'">
                                          AND b.link not like 'https://%' AND b.link not like '%youtu%'
                                      </if>\s
                            </trim>
            </script>
                        
            """)
    int countAll(String member_id, String categoryTopics);

    @Update("""
            UPDATE member 
            SET nickname = #{nickname},
                email = #{email},
                password = #{password},
                phone_number = #{phone_number}
            WHERE member_id = #{member_id}
            """)
    boolean updateMemberInformation(String member_id, String nickname, String email, String password, String phone_number);

    @Select("""
            SELECT password FROM member WHERE member_id = #{member_id}
            """)
    String getPasswordByMemberId(String member_id);

    @Select("""
            SELECT member_id FROM member WHERE member_id = #{memberId}
            """)
    String searchMemberByMemberId(String memberId);

    @Select("""
            SELECT COUNT(id) FROM member_followers WHERE follower_id = #{follower} AND following_id = #{following}
            """)
    int isFollowing(String follower, String following);

    @Insert("""
            INSERT INTO member_followers (follower_id, following_id)
            VALUES (#{follower}, #{following})
            """)
    boolean addFollowing(String follower, String following);

    @Select("""
SELECT
    m.id,
    m.member_id,
    m.nickname,
    m.email,
    m.phone_number,
    r.role_name,
    m.gender,
    m.birth_date,
    (SELECT COUNT(bl2.id)
     FROM boardlike bl2
              JOIN board b2 ON bl2.board_id = b2.id
     WHERE b2.board_member_id = m.member_id) AS total_like,
    (SELECT SUM(views)
     FROM board
     WHERE board_member_id = m.member_id) AS total_views,
    (SELECT COUNT(id)
     FROM board
     WHERE board_member_id = m.member_id) AS total_board,
    (SELECT COUNT(c2.id)
     FROM comment c2
     WHERE c2.member_id = m.member_id) AS total_comment,
    (SELECT mp2.image_name
     FROM memberprofileimagefile mp2
     WHERE mp2.member_id = m.member_id
     ORDER BY mp2.id DESC LIMIT 1) AS image_name,
    (SELECT mp2.id
     FROM memberprofileimagefile mp2
     WHERE mp2.member_id = m.member_id
     ORDER BY mp2.id DESC LIMIT 1) AS image_id
FROM member m
         LEFT JOIN roles r ON m.role_id = r.role_id
WHERE m.member_id IN (
    SELECT following_id
    FROM member_followers
    WHERE follower_id = #{memberId}
)
GROUP BY m.id, m.member_id, m.nickname, m.email, m.phone_number, r.role_name, m.gender, m.birth_date
ORDER BY m.nickname;
            """)
    List<MemberDTO> getFollowingListByMemberId(String memberId);


    @Select("""
            SELECT
                m.id,
                m.member_id,
                m.nickname,
                m.email,
                m.phone_number,
                r.role_name,
                m.gender,
                m.birth_date,
                (SELECT COUNT(bl2.id)
                 FROM boardlike bl2
                          JOIN board b2 ON bl2.board_id = b2.id
                 WHERE b2.board_member_id = m.member_id) AS total_like,
                (SELECT SUM(views)
                 FROM board
                 WHERE board_member_id = m.member_id) AS total_views,
                (SELECT COUNT(id)
                 FROM board
                 WHERE board_member_id = m.member_id) AS total_board,
                (SELECT COUNT(c2.id)
                 FROM comment c2
                 WHERE c2.member_id = m.member_id) AS total_comment,
                (SELECT mp2.image_name
                 FROM memberprofileimagefile mp2
                 WHERE mp2.member_id = m.member_id
                 ORDER BY mp2.id DESC LIMIT 1) AS image_name,
                (SELECT mp2.id
                 FROM memberprofileimagefile mp2
                 WHERE mp2.member_id = m.member_id
                 ORDER BY mp2.id DESC LIMIT 1) AS image_id
            FROM member m
                     LEFT JOIN roles r ON m.role_id = r.role_id
            WHERE m.member_id IN (
                SELECT follower_id
                FROM member_followers
                WHERE following_id = #{memberId}
            )
            GROUP BY m.id, m.member_id, m.nickname, m.email, m.phone_number, r.role_name, m.gender, m.birth_date
            ORDER BY m.nickname;
            
            """)
    List<MemberDTO> getFollowerListByMemberId(String memberId);

    @Delete("""
            DELETE FROM member_followers WHERE following_id = #{followingId} AND follower_id = #{followerId};
            """)
    boolean deleteFollowing(String followingId, String followerId);
}
