package com.safevoice.controller.Alert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mysql.cj.Session;
import com.safevoice.controller.Command;
import com.safevoice.db.AlertDAO;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.AlertVO;
import com.safevoice.model.MemberVO;

public class GetAlertHistoryService implements Command {
	
	// 알림 내역

	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 아이디 전역 변수 처리
		String id = "";
		// 조회하려는 자녀 아이디를 session 에서 가져오기. 
		HttpSession session = request.getSession();
		List<MemberVO> childList = (List<MemberVO>) session.getAttribute("childList");
		if(childList != null && !childList.isEmpty()) {
			MemberVO lastChild = childList.get(childList.size()-1);
			id = lastChild.getId(); // 가장 최근 자녀의 아이디
		} else {
			// 자녀 리스트 비어 있음
		}
		
		// AlertDAO 생성
		AlertDAO adao = new AlertDAO();
		
		// adao의 getAlertHistory 메서드를 실행시키고 결과값을 list에 저장
		List<AlertVO> alertList = adao.getAlertHistory(id);
		
		// MemberDAO 생성
		//MemberDAO dao = new MemberDAO();
		//ring childName = dao.getChildNameById(id);
		
		// 결과 맵 구성
		//Map<String, Object> resultMap = new HashMap<>();
		//resultMap.put("childName", childName);
		//resultMap.put("alerts", alertList);
		
		// List 형태로 온 데이터를 json 형식으로 변경
		Gson gson = new Gson();
		String alertJson = gson.toJson(alertList);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(alertJson);
		
		return null;
	}

}
