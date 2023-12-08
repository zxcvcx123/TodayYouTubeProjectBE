package com.example.pj2be.mapper.membermapper;

import com.example.pj2be.domain.board.BoardDTO;
import com.example.pj2be.domain.member.MemberUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
