package com.safevoice.controller.Member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class LoginService implements Command {

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 로그인

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		MemberVO mvo = new MemberVO();
		mvo.setId(id);
		mvo.setPw(pw);

		MemberDAO mdao = new MemberDAO();
		
		MemberVO resultVo = mdao.login(mvo);
		
		HttpSession session = request.getSession();
		
		if (resultVo == null) {
		    session.setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
		    return "redirect:/GoLogin.do";
		}

		String memType = resultVo.getMemType();
		
		System.out.println("로그인 시도 결과 ID: " + resultVo.getId());
		System.out.println("로그인 시도 결과 memType: " + resultVo.getMemType());
		System.out.println("✅ 로그인 성공! memType: " + memType);
		
		if (resultVo != null) {
			List<MemberVO> childList = mdao.selectMyChildren(resultVo.getId()); // 로그인한 회원의 아이리스트
			session.setAttribute("loginMember", resultVo); // 로그인한 회원 정보
			session.setAttribute("loginId", resultVo.getId()); // 로그인한 회원의 아이디
			session.setAttribute("childList", childList);
			if (memType.trim().equals("P")) {
				return "redirect:/MainPageAdult.do";
			} else if (memType.trim().equals("C")) {
				return "redirect:/MainPageChild.do";
			} else {
		        // 혹시 모를 예외적인 경우 처리
		        session.setAttribute("error", "회원 유형이 잘못되었습니다.");
		        return "redirect:/GoLogin.do";
		    }
		}
		else {
		    session.setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
		    return "redirect:/GoLogin.do";
		}
	}
}
