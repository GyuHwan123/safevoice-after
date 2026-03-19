package com.safevoice.controller.Member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;

	// 회원가입 시 ID 중복 체크

public class IdDuplicateCheckService implements Command {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try {
			// 조회하려는 아이디를 프론트에서 받아오기.
			String id = request.getParameter("username");
			
			// dao 호출
			MemberDAO dao = new MemberDAO();
			
			// DB로 중복 여부 확인
			int row = dao.idDuplicateCheck(id);
			
			// JSON 응답 생성
			JSONObject json = new JSONObject();
			
			json.put("available", row<=0);
			
			// 클라이언트에 응답 전송
			response.getWriter().write(json.toString());
			
		} catch(Exception e) {
			e.printStackTrace();
			// 오류 발생 시 클라이언트에 실패 응답 전송
			JSONObject errorjson = new JSONObject();
			errorjson.put("available", false);
			errorjson.put("error", "Server error");
			response.getWriter().write(errorjson.toString());
		}
		
		return null;
	}
	
}
