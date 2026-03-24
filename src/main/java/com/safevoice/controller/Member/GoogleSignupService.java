package com.safevoice.controller.Member;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class GoogleSignupService implements Command {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("social_email");
		String name = (String) session.getAttribute("social_name");
		String id = (String) session.getAttribute("social_id");
		String memType = request.getParameter("memType");
		
		MemberVO vo = new MemberVO();

		vo.setId(id);      
		vo.setEmail(email);
		vo.setName(name);
		vo.setMemType(memType);
		
		MemberDAO mdao = new MemberDAO();
		
		mdao.insertGoogleMember(vo);
		
		if(memType.equals("P")){
            return "redirect:/GoMainPageAdult.do";
        }
        else{
            return "redirect:/GoMainPageChild.do";
        }
	}
}
