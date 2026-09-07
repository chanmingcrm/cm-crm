package com.platform.mesh.douyin.service.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @description  授权接口
 * @author 蝉鸣
 */
@Service
public class SignServiceManual {

    private final static Logger log = LoggerFactory.getLogger(SignServiceManual.class);

    /**
     * @Description AES解密
     * @param data base64后的密文
     * @return 明文
     */
    public String decryptAES(String secret, String data){
        String key = parseSecret(secret);
        String iv = key.substring(16);;
        try
        {
            //先用base64解密
            byte[] encrypted1 = decode(data);
            //再用AES解密
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * base64编码
     */
    private String encode(byte[] byteArray) {
        return new String(Base64.getEncoder().encode(byteArray));
    }

    /**
     * base64解码
     */
    private byte[] decode(String base64EncodedString) {
        return Base64.getDecoder().decode(base64EncodedString);
    }

    /**
     * base64解码
     */
    private String parseSecret(String secret) {
        secret = fillSecret(secret);
        secret = cutSecret(secret);
        return secret;
    }

    /**
     * 解析密钥
     */
    private String cutSecret(String secret) {
        if (secret.length() <= 32) {
            return secret;
        }
        int rightCnt = (secret.length() - 32) / 2;
        int leftCnt = secret.length() - 32 - rightCnt;
        return secret.substring(leftCnt, 32 + leftCnt);
    }

    /**
     * 解析密钥
     */
    private String fillSecret(String secret) {
        if (secret.length() >= 32) {
            return secret;
        }
        int rightCnt = (32 - secret.length()) / 2;
        int leftCnt = 32 - secret.length() - rightCnt;
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < leftCnt; i++) {
            sb.append('#');
        }
        sb.append(secret);
        for (int i = 0; i < rightCnt; i++) {
            sb.append('#');
        }
        return sb.toString();
    }

}
