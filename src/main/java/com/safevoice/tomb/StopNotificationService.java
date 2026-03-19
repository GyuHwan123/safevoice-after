package com.safevoice.tomb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;

// 알림 반복(타이머) 중단

public class StopNotificationService implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        NotificationScheduler.stop();  // 타이머 중단
        System.out.println("🛑 반복 중단 요청 도착!");
        return null;
    }
}