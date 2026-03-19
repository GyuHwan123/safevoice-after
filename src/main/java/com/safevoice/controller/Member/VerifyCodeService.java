package com.safevoice.controller.Member;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;


public class VerifyCodeService implements Command {
	
	// 자녀 연결 (자녀 메일로 간 코드랑 입력한 코드 일치하는지 확인)
	
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String realCode = (String) session.getAttribute("realCode");
		String inputCode = request.getParameter("inputCode");
        String loginId = (String) session.getAttribute("loginId");
        String receiverId = (String) session.getAttribute("receiverId");
        
        MemberDAO mdao = new MemberDAO();

        if (realCode != null && realCode.equals(inputCode)) {
            MemberVO vo = new MemberVO();
            vo.setId(loginId);          // 기준 ID
            vo.setChildId(receiverId);  // 연결 대상

            int row = mdao.updateFamilyCd(vo);

            if (row > 0) {
                request.setAttribute("successMsg", "인증 성공! 가족으로 연결되었습니다.");

                // ✅ 추가: 자녀관리 페이지에서 자녀가 바로 뜨도록 세션에 저장
                session.setAttribute("addedChildId", receiverId);

                // ✅ 이동도 자녀관리 페이지로 해야 즉시 반영됨
                session.removeAttribute("realCode");
                return "GoChildConfig.do";
            } else {
                request.setAttribute("errorMsg", "가족 등록에 실패했습니다. 다시 시도해주세요.");
                return "GoRequestConnection.do";
            }
        } else {
            request.setAttribute("errorMsg", "인증 코드가 일치하지 않습니다.");
            return "GoRequestConnection.do";
        }
    }
}