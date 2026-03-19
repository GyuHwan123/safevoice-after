//package com.safevoice.tomb;
//
//import java.security.KeyPair;
//
//public class GenerateVapidKey {
//    public static void main(String[] args) {
//        try {
//            KeyPair keyPair = Utils.generateVapidKeys();
//
//            String publicKey = Utils.encodePublicKey(keyPair.getPublic());
//            String privateKey = Utils.encodePrivateKey(keyPair.getPrivate());
//
//            System.out.println("\uD83D\uDD11 VAPID 공개키 (p256dh):\n" + publicKey);
//            System.out.println("\uD83D\uDD10 VAPID 비밀키 (auth):\n" + privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}