package com.safevoice.controller.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;


//	구독 정보 DB에 저장

public class SaveSubscriptionService implements Command {
	public static String lastSubscriptionJson = "";

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		try {
			BufferedReader reader = request.getReader();
			String json = reader.lines().collect(Collectors.joining());

			// 🧩 memberId도 함께 받아야 함 (파라미터로 넘겨야 함)
			String memberId = request.getParameter("memberId");

			if (memberId == null || memberId.isBlank()) {
				response.getWriter().write("❌ memberId가 없습니다.");
				return null;
			}

			// ✅ DB 저장용 객체 구성
			MemberVO mvo = new MemberVO();
			mvo.setId(memberId);
			mvo.setJsonSubscription(json);

			// ✅ DAO 호출
			MemberDAO dao = new MemberDAO();
			dao.saveSubscription(mvo); // 이 메서드 필요!

			System.out.println("📦 구독 정보 저장 완료: " + memberId);
			response.getWriter().write("💌 구독 정보 저장 성공!");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("💥 에러 발생: " + e.getMessage());
			} catch (IOException ignored) {
			}
		}
		return null;
	}
}