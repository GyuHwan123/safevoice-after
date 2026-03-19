package com.safevoice.controller.Member;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class VerifyIdentityService implements Command {
	
	// 비밀번호 변경 (본인확인)
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    	// 아이디 / 비밀번호 / 전화번호 입력받기
    	String id = request.getParameter("id");
        String phone = request.getParameter("phone");
        
        // 유저 정보 불러오기
        MemberDAO mdao = new MemberDAO();
        MemberVO mvo = new MemberVO();
        mvo.setId(id);
        mvo.setPhone(phone);
        
        // dao.findMember 실행
        int row = mdao.findMember(mvo);
        
        // 입력한 아이디 / 전화번호 > 해당하는 유저 있나 확인
        if (row != 0) {
        	// 동일
            request.setAttribute("id", id);
            return "GoChangePassword.do";
        } else {
            // 틀렸어!
            return "VerifyIdentity.jsp?error=true"; 
            // ㄴ 요거 수정? 을? 흠... 고민중입니다... 쿼리 파라미터를 붙이는게 맞을지.,.. 쿼리파라미터 이해가잘안돼서... 고민중...
        }
    }

}