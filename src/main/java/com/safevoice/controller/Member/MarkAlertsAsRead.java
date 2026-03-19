package com.safevoice.controller.Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.safevoice.controller.Command;
import com.safevoice.db.AlertDAO;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

//	VIEW_OR_NOT (확인 여부) 설정

public class MarkAlertsAsRead implements Command {

	public String execute (HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = "";
		// 조회하려는 아이디를 session 에서 가져오기. 
		HttpSession session = request.getSession();
		List<MemberVO> childList = (List<MemberVO>) session.getAttribute("childList");
		if(childList != null && !childList.isEmpty()) {
			MemberVO lastChild = childList.get(childList.size()-1);
			id = lastChild.getId(); // 가장 최근 자녀의 아이디
		} else {
			// 자녀 리스트 비어 있음
		}
		
		AlertDAO adao = new AlertDAO();
		int row = adao.MarkAlertsAsRead(id);
		
		if(row >0) {
			response.setStatus(200);
		} else {
			response.setStatus(401);
		}
		
	
		return null;
	}
	
}
