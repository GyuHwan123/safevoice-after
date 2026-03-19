package com.safevoice.controller.Member;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class SignOutService extends HttpServlet implements Command {
	private static final long serialVersionUID = 1L;
	
	// 회원탈퇴 (구현 X)
       
	public String execute(HttpServletRequest request, HttpServletResponse response) {
	    String id = request.getParameter("id");
	    String pw = request.getParameter("pw");

	    MemberDAO mdao = new MemberDAO();
	    MemberVO mvo = new MemberVO();
	    mvo.setId(id);
	    mvo.setPw(pw);

	    int row = mdao.signOut(mvo);

	    HttpSession session = request.getSession(false);

	    if (row > 0) {
	        if (session != null) {
	            session.invalidate(); // 세션 날리고
	            session = request.getSession(true); // 새로 만들고
	            session.setAttribute("logoutMsg", "👋 회원 탈퇴가 완료되었습니다. 그동안 고마웠어요!");
	        }
	        return "redirect:/GoLogin.do";
	    } else {
	        session.setAttribute("logoutMsg", "🚫 회원 탈퇴에 실패했습니다. 비밀번호를 확인해주세요!");
	        return "redirect:/GoMenuMain.do"; // 또는 탈퇴 페이지로
	    }
	}

    }
