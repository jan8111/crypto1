package client;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * <p>
 * 封装同RSA非对称加密算法有关的方法，可用于数字签名，RSA加密解密
 * </p>
 *
 * @Copyright:WDSsoft
 */

public class RSATool {


    /**
     * 建立新的密钥对，返回打包的byte[]形式私钥和公钥
     *
     * @return 包含打包成byte[]形式的私钥和公钥的object[], 其中，object[0]为私钥byte[],object[1]为公钥byte[]
     */
    public static Object[] giveRSAKeyPairInByte() {
        KeyPair newKeyPair = creatmyKey();
        if (newKeyPair == null)
            return null;
        Object[] re = new Object[2];
        if (newKeyPair != null) {
            PrivateKey priv = newKeyPair.getPrivate();
            byte[] b_priv = priv.getEncoded();
            PublicKey pub = newKeyPair.getPublic();
            byte[] b_pub = pub.getEncoded();
            re[0] = b_priv;
            re[1] = b_pub;
            return re;
        }
        return null;
    }

    /**
     * 新建密钥对
     *
     * @return KeyPair对象
     */
    public static KeyPair creatmyKey() {
        KeyPair myPair;
        long mySeed;
        mySeed = System.currentTimeMillis();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            random.setSeed(mySeed);
            keyGen.initialize(1024, random);
            myPair = keyGen.generateKeyPair();
        } catch (Exception e1) {
            return null;
        }
        return myPair;
    }


    /**
     * 使用RSA私钥加密数据
     *
     * @param privKeyInByte 打包的byte[]形式私钥
     * @param data          要加密的数据
     * @return 加密数据
     */
    public static byte[] encryptByRSA1(byte[] privKeyInByte, byte[] data) throws Exception {

        PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(
                privKeyInByte);
        KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
        Cipher cipher = Cipher.getInstance(mykeyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privKey);
        return cipher.doFinal(data);


    }

    /**
     * 用RSA公钥解密
     *
     * @param pubKeyInByte 公钥打包成byte[]形式
     * @param data         要解密的数据
     * @return 解密数据
     */
    public static byte[] decryptByRSA1(byte[] pubKeyInByte, byte[] data) throws Exception {

        KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(pubKeyInByte);
        PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
        Cipher cipher = Cipher.getInstance(mykeyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);

    }


    public static void main(String[] args) throws Exception {
        byte[] data = "Jh2BB9KuxCfD49znxuZvvn1KlPzJcesZizyCxKQZbjCwFDXBe5FGv8c9kuxPo3J/rzBxRyG0OeuboiKaxgknF2xhxK5u0mKSSfIZCrM5v8EOTLEHb6PAlbiNYS8Yq8dPmXUX3QvnI2TdFtbvzJ8vRllPslGvvynKL2UGO2Owi5/3pBjZTXLsibMzAzZzK0L8pAHE02Lnses3iAJuq/6Nb0N5OX4X73I9+5zNYxBMLEDOWxHidcDoLPvquMspwM+uzHtIpLSymnmiPEA1ZxPah7vlo95NUxi4EFRD/NVMEbziLCo0Oav6DejqCy03eA2KHYzSP0HQ8qflpK8jT9TyhA==".getBytes();
        data=Base64.getDecoder().decode(data);
        byte[] newSourcepri_pub = decryptByRSA1(getpubkey("E:\\Projects-test\\vpshsdk1\\src\\main\\java\\client\\pubkey.pem"), data);
        System.out.println("公钥解密：" + new String(newSourcepri_pub, "UTF-8"));
    }

    public static byte[] getpubkey(String filePath) throws Exception {
        BASE64Decoder base64decoder = new BASE64Decoder();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = br.readLine();
        StringBuffer publickey = new StringBuffer();
        s = br.readLine();
        while (s.charAt(0) != '-') {
            publickey.append(s + "\r");
            s = br.readLine();
        }
        return base64decoder.decodeBuffer(publickey.toString());

    }


}

