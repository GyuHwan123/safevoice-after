package com.safevoice.controller.Member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

@MultipartConfig
public class UpdateMemberService implements Command {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("오류1");
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");

		System.out.println("오류2");

		// 2. 폼에서 전달된 새 정보 받기

		String newPw = request.getParameter("pw");
		String newPostCode = request.getParameter("postcode");
		String newAddress = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		System.out.println(newPw);
		System.out.println(newPostCode);
		System.out.println(newAddress);
		System.out.println(detailAddress);

		// paravo 객체를 만들어서 데이터 담기
		MemberVO paravo = new MemberVO();
		paravo.setId(loginId);
		paravo.setPw(newPw);
		paravo.setPostcode(newPostCode);
		paravo.setAddress(newAddress);
		paravo.setDetailAddress(detailAddress);

		System.out.println("오류3");
		System.out.println("memvo : " + paravo.getPw());
		System.out.println("memvo : " + paravo.getPostcode());
		// dao 선언
		MemberDAO dao = new MemberDAO();
		int row = dao.updateMember(paravo);
		System.out.println(row);
		if (row > 0) {
			// 세션에서 로그인된 회원 정보 가져오기
			loginMember.setPw(newPw);
			loginMember.setPostcode(newPostCode);
			loginMember.setAddress(newAddress);
			loginMember.setDetailAddress(detailAddress);
			System.out.println("오류4");
			// 갱신된 loginMember를 세션에 다시 저장
			session.setAttribute("loginMember", loginMember);
			System.out.println("오류5");
			response.getWriter().write("success");
		} else {
			response.getWriter().write("fail");
		}

		String memType = loginMember.getMemType();
		if (memType.trim().equals("P")) {
			return "redirect:/GoMainPageAdult.do";
		} else if (memType.trim().equals("C")) {
			return "redirect:/GoMainPageChild.do";
		} else {
			// 혹시 모를 예외적인 경우 처리
			session.setAttribute("error", "회원 유형이 잘못되었습니다.");
			return "redirect:/GoLogin.do";
		}

	}
}
