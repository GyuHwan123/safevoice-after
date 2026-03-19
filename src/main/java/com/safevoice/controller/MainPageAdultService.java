package com.safevoice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageAdultService implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션 검사는 필요시 추가
        return "GoMainPageAdult.do";  // ✅ 여기서 다시 JSP로 이동시켜야 함!
    }
}
