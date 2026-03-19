package com.safevoice.tomb;
//
//import com.google.gson.Gson;
//import com.safevoice.controller.Command;
//import nl.martijndwars.webpush.Notification;
//import nl.martijndwars.webpush.PushService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.PublicKey;
//import java.security.PrivateKey;
//import org.apache.commons.codec.binary.Base64;
//
//public class SendPushNotificationService implements Command {
//
//	public String execute(HttpServletRequest request, HttpServletResponse response) {
//		send(); // 수동 실행 시에도 같은 메서드 사용
//		try {
//			response.setContentType("text/plain; charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write("💌 수동 알림 전송 완료!");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public void send() {
//		try {
//			String json = SaveSubscriptionService.lastSubscriptionJson;
//			if (json == null || json.isEmpty()) {
//				System.out.println("❌ 구독 정보 없음 - 알림 전송 불가");
//				return;
//			}
//
//			MySubscription sub = new Gson().fromJson(json, MySubscription.class);
//
//			String endpoint = sub.getEndpoint();
//			byte[] authBytes = Base64.decodeBase64(sub.getKeys().getAuth());
//			PublicKey userPubKey = Utils.loadPublicKey(sub.getKeys().getP256dh());
//
//			PrivateKey vapidPrivateKey = Utils.loadPrivateKey("H-TuprUCHJgjzR4YuHHEahaM6Uv2PfeGS5rtfbgfSoQ");
//			PublicKey vapidPublicKey = Utils.loadPublicKey(
//					"BBDwxhxTwL6k00-0sCIUW4mZhDlJt2R9jjAd2msSkh_52GZg8LeVKFcNBc7r__UOuI4_3RzrMvSdAjFIyZ0uEjI");
//
//			String payload = new Gson().toJson(new NotificationPayload("🛎️ 테스트 알림", "지금 도착했어요!"));
////            System.out.println("📦 payload 내용: " + payload); 넘어오나 확인용
//
//			byte[] payloadBytes = payload.getBytes("UTF-8");
////            System.out.println("📏 payload 길이: " + payloadBytes.length); 넘어오나 확인용
//
//			Notification notification = new Notification(endpoint, userPubKey, authBytes, payloadBytes, 255);
//
//			PushService pushService = new PushService();
//			pushService.setPublicKey(vapidPublicKey);
//			pushService.setPrivateKey(vapidPrivateKey);
//			pushService.setSubject("mailto:admin@example.com");
//
////			try {
//			pushService.send(notification);
//			System.out.println("🔥 서버에서 알림 전송 시도 완료!");
////			} catch (Exception e) {
////				e.printStackTrace();
////				System.err.println("💥 알림 전송 실패!! 이유: " + e.getMessage());
////			}
////            response.setContentType("text/plain; charset=UTF-8");
////            response.setCharacterEncoding("UTF-8");
////            response.getWriter().write("💌 알림 전송 완료!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////            try {
////                response.getWriter().write("💥 전송 실패: " + e.getMessage());
////            } catch (IOException ignored) {}
////        }
//
//	}
//
//	static class NotificationPayload {
//		String title;
//		String body;
//
//		NotificationPayload(String title, String body) {
//			this.title = title;
//			this.body = body;
//		}
//	}
//}


