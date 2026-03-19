package com.safevoice.controller.Alert;

import com.safevoice.db.AlertDAO;

import java.util.List;
	
	// 알림 전송을 위한 구독 정보 (알림 송신 허용 여부) 확인
	
public class PushNotificationService {

    public void sendNotification(String childId, String title, String body) {
        try {
            // 자녀 ID를 기준으로 부모들의 구독 정보 리스트 조회
            AlertDAO dao = new AlertDAO();
            List<String> parentSubscriptions = dao.findParentSubscriptions(childId);

            if (parentSubscriptions == null || parentSubscriptions.isEmpty()) {
                System.out.println("❌ [부모 구독 없음] childId: " + childId);
                return;
            }

            for (String json : parentSubscriptions) {
                try {
                    PushNotificationSender.send(json, title, body);
                    System.out.println("✅ [부모에게 알림 전송 완료] childId: " + childId);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("🚨 [알림 전송 실패 - 개별] childId: " + childId + " 이유: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("🚨 [알림 전송 실패 - 전체] childId: " + childId + " 이유: " + e.getMessage());
        }
    }
}
