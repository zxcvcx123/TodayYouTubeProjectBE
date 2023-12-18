package com.example.pj2be.config.security;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AESEncryption {
    // 키 생성
    public static SecretKey generateKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    // 멤버 정보 암호화
    public static String encrypt(String member_id, SecretKey key)  throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(member_id.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 복호화
    public static String decrypt(String member_id, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(member_id));
        return new String(decryptedBytes);
    }


}
