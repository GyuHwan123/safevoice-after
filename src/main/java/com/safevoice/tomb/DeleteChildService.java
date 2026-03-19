package com.safevoice.tomb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.safevoice.controller.Command;
import com.safevoice.db.MemberDAO;

public class DeleteChildService implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String childId = request.getParameter("childId");

        MemberDAO dao = new MemberDAO();
        int row = dao.disconnectChild(childId);

        if (row > 0) {
            System.out.println("자녀 연결 해제 완료: " + childId);
        } else {
            System.out.println("자녀 연결 해제 실패...");
        }

        return "redirect:/ViewChildList.do"; // 자녀 목록 새로고침
    }
}
