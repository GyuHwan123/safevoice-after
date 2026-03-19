package com.safevoice.controller.Alert;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;

	// PushService Key 세팅 / 알림 전송 준비

public class PushNotificationSender {
	
	 private static final String VAPID_PUBLIC_KEY = "BK47ejYRaSe6EDUclmUHMrqznzElZBnfY7CasYcaTQKWpZQe4BBUNNdqmBo2lR4sVd4m2aLuBHvYo44gwxbjogw"; // 추후에 환경변수에서 로드
	 private static final String VAPID_PRIVATE_KEY = "E4eeFQbM6SWNFxXLzhBIwpT51CuszqeGD0U04uFr0xU"; // 추후에 환경변수에서 로드

	 public static void send(String subscriptionJson, String title, String body) throws Exception {
	        MySubscription sub = new Gson().fromJson(subscriptionJson, MySubscription.class);
	        
	        
	        String endpoint = sub.getEndpoint();
			byte[] authBytes = Base64.decodeBase64(sub.getKeys().getAuth());
			PublicKey userPubKey = Utils.loadPublicKey(sub.getKeys().getP256dh());
	        PrivateKey vapidPrivateKey = Utils.loadPrivateKey(VAPID_PRIVATE_KEY);
	        PublicKey vapidPublicKey = Utils.loadPublicKey(VAPID_PUBLIC_KEY);
	        
	        String payload = new Gson().toJson(new NotificationPayload(title, body));
	        byte[] payloadBytes = payload.getBytes("UTF-8");
	        
	        Notification notification = new Notification(
	                endpoint, userPubKey, authBytes, payloadBytes, 255);
	        
	        PushService pushService = new PushService();
	        pushService.setPublicKey(vapidPublicKey);
	        pushService.setPrivateKey(vapidPrivateKey);
	        pushService.setSubject("mailto:admin@example.com");

	        pushService.send(notification);
	        
	    }

	    static class NotificationPayload {
	        String title;
	        String body;
	        NotificationPayload(String title, String body) {
	            this.title = title;
	            this.body = body;
	        }
	    }
	
}
