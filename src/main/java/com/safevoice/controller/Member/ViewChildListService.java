package com.safevoice.controller.Member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;
import com.safevoice.model.MemberVO;

//	자녀 관리 - 리스트 보이기

public class ViewChildListService implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");

        MemberDAO dao = new MemberDAO();
        List<MemberVO> childrenList = dao.selectMyChildren(loginId);

        request.setAttribute("childrenList", childrenList);

        return "GoChildConfig.do";
    }
}