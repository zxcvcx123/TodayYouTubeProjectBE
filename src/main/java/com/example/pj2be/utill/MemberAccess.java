package com.example.pj2be.utill;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MemberAccess {

    public static boolean IsLoginMember(String loginId) {
        // memberId 넣으면 로그인 여부 확인 가능
        if ((loginId != null) && (loginId.length() > 0) && (!loginId.isBlank())) {
            return true;
        } else {
            return false;
        }

    }
    /** 첫번째 파라미터 = 로그인 정보 아이디, <br>
     * 두번째 파라미터 = 작성자 아이디 <br>
     * 두 파라미터가 서로 같은지 검증하여 ResponseEntity타입 반환 <br>
     * 전부 통과 : 0 반환 <br>
     * 로그인 정보 아이디 없음 : 1반환 <br>
     * 아이디가 서로 다름 : 2 반환 <br>
     * 다 실패 : 3 반환
     */
    public static Integer MemberChecked(String loginId, String writerId) {

        if ((loginId.length() > 0) && (writerId.length() > 0)) {
            if (loginId.equals(writerId)) {
                return 0;
            }
            if (loginId.isBlank()) {
                return 1;
            }
            return 2;
        }
        return 3;
    }

}
