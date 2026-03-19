package com.safevoice.tomb;

import java.util.Timer;
import java.util.TimerTask;

//import com.safevoice.controller.Alert.SendPushNotificationService;

public class NotificationScheduler {

    private static Timer timer;
    private static int intervalMillis;

    // 🔁 반복 시작
    public static void start() {
        stop();  // 기존 타이머 종료
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("🔁 반복 알림 전송 중...");
//                    new SendPushNotificationService().send();  // 알림 전송 (execute 대신 send 메서드로 분리 권장)
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, intervalMillis);
    }

    // ⏱️ 주기 변경 (분 단위)
    public static void updateInterval(int minutes) {
        if (minutes < 1 || minutes > 10) {
            System.out.println("⚠️ 주기는 1~10분 사이여야 합니다!");
            return;
        }
        intervalMillis = minutes * 60 * 1000;
        start();
    }

    // 🛑 반복 중단
    public static void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            System.out.println("🛑 반복 알림 중단됨!");
        }
    }
}

