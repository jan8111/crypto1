package client;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA256Test {

    public static void main(String[] args) throws Exception {
        String ret1 = HMACSHA256("Message".getBytes(), "secret".getBytes());
        System.out.println("ret1 = " + ret1);
    }

    public static String HMACSHA256(byte[] data, byte[] key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        return byte2hex(mac.doFinal(data));
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
}
