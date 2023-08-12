package com.example.webapi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {
    private static final String ALGORITHM = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    private static final String aesKeyFilePath = "aes_key_file.key";

    private static final String aesIVFilePath = "aes_iv_key_file.key";

    public static String readAesKeyFromFile() {
        try {
            File aesKeyFile = new File(aesKeyFilePath);
            if (!aesKeyFile.exists()) {
                throw new RuntimeException("File chứa AES key không tồn tại.");
            }
            // Đọc nội dung từ file
            StringBuilder aesKeyContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(aesKeyFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    aesKeyContent.append(line);
                }
            }
            return aesKeyContent.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAesKey() {
        return readAesKeyFromFile();
    }

    public static String readAesIVKeyFromFile() {
        try {
            File aesIVKeyFile = new File(aesIVFilePath);
            if (!aesIVKeyFile.exists()) {
                throw new RuntimeException("File chứa AES IV key không tồn tại.");
            }
            // Đọc nội dung từ file
            StringBuilder aesIVKeyContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(aesIVKeyFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    aesIVKeyContent.append(line);
                }
            }
            return aesIVKeyContent.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAesIVKey() {
        return readAesIVKeyFromFile();
    }

    public static String encryptAES(String input, String key, String iv) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            System.out.println("AESUtils encryptAES: " + ex.getMessage() + "");
            return null;
        }
    }

    public static String decryptAES(String encryptAES, String key, String iv) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_CBC);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder()
                    .decode(encryptAES));
            return new String(decrypted);
        } catch (Exception ex) {
            System.out.println("AESUtils encryptAES: " + ex + "");
            return null;
        }
    }
}
