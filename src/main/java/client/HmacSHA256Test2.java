package client;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static client.HmacSHA256Test.byte2hex;

public class HmacSHA256Test2 {

    public static void main(String[] args) throws Exception {
        String str1 = HMACSHA256();
        System.out.println("str1 = " + str1);
    }

    public static String HMACSHA256() throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec("secret".getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        readFileByBytes((data1, len1) -> mac.update(data1, 0, len1));
        return byte2hex(mac.doFinal());
    }


    public static void readFileByBytes(Consume2r<byte[], Integer> consumer) throws IOException {
        File file = new File("E:\\Projects-test\\vpshsdk1\\src\\main\\resources\\message.txt");

        InputStream in = new FileInputStream(file);
        byte[] tempbyte = new byte[4];

        int len = 0;
        while ((len = in.read(tempbyte)) != -1) {
            consumer.accept(tempbyte, len);
        }
        in.close();
    }


}

@FunctionalInterface
interface Consume2r<T, L> {
    void accept(T t, L l);
}

