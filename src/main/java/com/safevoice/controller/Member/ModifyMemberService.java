package com.safevoice.controller.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class ModifyMemberService implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
    	
    	// 회원 정보 수정 (이메일 / 전화번호 / 주소)
    	
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        MemberVO mvo = new MemberVO();
        mvo.setId(id); // WHERE 조건 걸 id
         
        
        if (email != null && !email.isBlank()) {
            mvo.setEmail(email);
        }

        if (phone != null && !phone.isBlank()) {
            mvo.setPhone(phone);
        }
        
 
        
        if (address != null && !address.isBlank()) { // 주소 변경을 어떻게 하면 좋을지... 에 대한 고민
            mvo.setAddress(address);
        }
        
        if ((email == null || email.isBlank()) &&
        	    (phone == null || phone.isBlank()) &&
        	    (address == null || address.isBlank())) {
        	    
        	    request.setAttribute("errorMsg", "수정할 내용이 없습니다.");
        	    return "GoModifyMember.do";
        	}

        int row = new MemberDAO().updateMember(mvo);

        if (row > 0) {
            request.setAttribute("successMsg", "회원 정보 수정 완료!");
            return "GoMyPage.do"; // 마이페이지로 이동
            
        } else {
            request.setAttribute("FailMsg", "수정 실패");
            return "GoModifyMember.do";
        }
    }

}
