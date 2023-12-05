package com.example.pj2be.utill;

public class MemeberAccess {

    public static boolean IsLoginMember(String memberId) {
        // memberId 넣으면 로그인 여부 확인 가능
        if (memberId != null) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean MemberChecked(String loginId, String writerId) {

        // memberId가 있는경우 첫번째 파라미터에 넣고
        // 작성자를 두번째 파라미터에 넣어서 서로 같은지 검증

        if (loginId.length() > 0 && writerId.length() > 0) {
            if (loginId.equals(writerId)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
