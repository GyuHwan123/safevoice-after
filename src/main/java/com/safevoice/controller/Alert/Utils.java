package com.safevoice.controller.Alert;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;

//	PushService Key 세팅

public class Utils {

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // 🔐 VAPID 키쌍 생성 (secp256r1)
    public static KeyPair generateVapidKeys() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("P-256");
        generator.initialize(ecSpec);
        return generator.generateKeyPair();
    }

    // 📤 공개키를 URL-safe Base64로 인코딩
    public static String encodePublicKey(PublicKey publicKey) {
        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        ECPoint point = ecPublicKey.getW();
        EllipticCurve curve = ecPublicKey.getParams().getCurve();

        int fieldSize = (curve.getField().getFieldSize() + 7) / 8;
        byte[] x = padToSize(point.getAffineX().toByteArray(), fieldSize);
        byte[] y = padToSize(point.getAffineY().toByteArray(), fieldSize);

        byte[] encoded = new byte[1 + fieldSize * 2];
        encoded[0] = 0x04;
        System.arraycopy(x, 0, encoded, 1, fieldSize);
        System.arraycopy(y, 0, encoded, 1 + fieldSize, fieldSize);

        return Base64.encodeBase64URLSafeString(encoded);
    }

    // 📤 개인키를 URL-safe Base64로 인코딩 (raw scalar only)
    public static String encodePrivateKey(PrivateKey privateKey) {
        ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;
        BigInteger s = ecPrivateKey.getS();
        return Base64.encodeBase64URLSafeString(s.toByteArray());
    }

    // 📥 공개키 복원
    public static PublicKey loadPublicKey(String base64) throws Exception {
        byte[] decoded = java.util.Base64.getUrlDecoder().decode(base64);  // ✅ FIXED

        ECNamedCurveParameterSpec params = ECNamedCurveTable.getParameterSpec("P-256");
        org.bouncycastle.math.ec.ECPoint bcPoint = params.getCurve().decodePoint(decoded);
        ECPublicKeySpec pubSpec = new ECPublicKeySpec(bcPoint, params);

        KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
        return kf.generatePublic(pubSpec);
    }


    // 📥 개인키 복원
    public static PrivateKey loadPrivateKey(String base64) throws Exception {
        byte[] decoded = java.util.Base64.getUrlDecoder().decode(base64);  // ✅ FIXED
        BigInteger s = new BigInteger(1, decoded);
        ECNamedCurveParameterSpec params = ECNamedCurveTable.getParameterSpec("P-256");
        ECPrivateKeySpec privSpec = new ECPrivateKeySpec(s, params);
        KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
        return kf.generatePrivate(privSpec);
    }


    private static byte[] padToSize(byte[] src, int size) {
        if (src.length == size) return src;
        byte[] dest = new byte[size];
        System.arraycopy(src, Math.max(0, src.length - size), dest, size - Math.min(size, src.length), Math.min(size, src.length));
        return dest;
    }
}