package com.safevoice.controller.Member;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

public class ChangePasswordService implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1) 항상 plain text 응답
		response.setContentType("text/plain; charset=UTF-8");

		// 2) 파라미터 수신
		String id = request.getParameter("id");
		String updatePw = request.getParameter("updatePw");
		String confirmPw = request.getParameter("confirmPassword");

		// 3) 비밀번호 일치 여부 및 파라미터 누락 검사
		if (id == null || id.isEmpty() || updatePw == null || confirmPw == null || !updatePw.equals(confirmPw)) {
			response.getWriter().write("error");
			return null;
		}

		// 4) DB 업데이트
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPw(updatePw);

		MemberDAO dao = new MemberDAO();
		int row = dao.updatePw(vo);

		// 5) 결과에 따라 "success" 또는 "error"만 응답
		response.getWriter().write(row > 0 ? "success" : "error");
		return null;
	}
}
