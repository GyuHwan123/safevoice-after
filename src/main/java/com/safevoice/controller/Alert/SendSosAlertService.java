package com.safevoice.controller.Alert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safevoice.controller.Command;
import com.safevoice.db.AlertDAO;
import com.safevoice.model.AlertVO;

public class SendSosAlertService implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("memberId");
        System.out.println("📥 SOS 요청 수신됨! memberId = " + memberId);

        if (memberId == null || memberId.isBlank()) {
            try { response.setStatus(400); } catch (Exception ignored) {}
            return null;
        }

        // DB에 SOS 저장
        AlertVO alert = new AlertVO();
        alert.setMemberId(memberId);
        alert.setAlertType("SOS");
        alert.setLat("0.0"); // 추후 위치 연동 가능
        alert.setLon("0.0");

        AlertDAO dao = new AlertDAO();
        dao.setAlertInfo(alert);
        int row = dao.setAlertInfo(alert);
        System.out.println("📝 DB 저장 성공 여부 (row): " + row);

        // 알림 전송도 하고 싶다면 아래 추가
        PushNotificationService pushService = new PushNotificationService();
        System.out.println("📤 푸시 알림 전송 시작!");
        pushService.sendNotification(memberId, "🔴 긴급 SOS!", "자녀가 SOS 버튼을 눌렀습니다!");
        System.out.println("✅ 푸시 알림 전송 완료!");
        
        return null;
    }
}
