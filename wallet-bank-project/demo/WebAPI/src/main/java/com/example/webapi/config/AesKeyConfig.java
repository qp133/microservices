package com.example.webapi.config;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class AesKeyConfig {

    @Value("${aes.key.file.path}")
    private String aesKeyFilePath;

    @PostConstruct
    public void generateAndSaveAESKey() {
        // Kiểm tra xem đã tạo AES key hay chưa, nếu đã tạo thì không cần tạo lại
        if (isAESKeyExist()) {
            return;
        }

        try {
            // Tạo AES key ngẫu nhiên
            byte[] aesKey = generateRandomAESKey();

            // Chuyển đổi AES key thành chuỗi hex
            String aesKeyHex = convertAESKeyToHex(aesKey);

            // Lưu AES key vào file
//            saveAESKeyToFile(aesKey);
            saveHexAESKeyToFile(aesKeyHex);

            System.out.println("Random AES key generated and saved to files.");
            System.out.println("AES key is: " + aesKeyHex);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tạo hoặc lưu trữ AES key.");
        }
    }

    private byte[] generateRandomAESKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] aesKey = new byte[32]; // Độ dài AES key là 256 bit (32 bytes)
        secureRandom.nextBytes(aesKey);
        return aesKey;
    }

//    private void saveAESKeyToFile(byte[] aesKey) throws IOException {
//        File aesKeyFile = new File(aesKeyFilePath);
//        FileOutputStream fos = new FileOutputStream(aesKeyFile);
//        fos.write(aesKey);
//        fos.close();
//    }

    private String convertAESKeyToHex(byte[] aesKey) {
        return Hex.encodeHexString(aesKey);
    }

    private void saveHexAESKeyToFile(String hexAESKey) throws IOException {
        File aesKeyFile = new File(aesKeyFilePath);
        hexAESKey = hexAESKey.substring(0,16);
        FileOutputStream fos = new FileOutputStream(aesKeyFile);
        fos.write(hexAESKey.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    private boolean isAESKeyExist() {
        File aesKeyFile = new File(aesKeyFilePath);
        return aesKeyFile.exists();
    }

}