package com.safevoice.tomb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;

public class RepeatAlertService implements Command {
	public String execute (HttpServletRequest request, HttpServletResponse response) {
		
		try {
            int minutes = Integer.parseInt(request.getParameter("minutes"));
            NotificationScheduler.updateInterval(minutes);
            response.getWriter().write("🔁 알림 반복 시작 (" + minutes + "분 간격)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/Goindex.do";
    }
}