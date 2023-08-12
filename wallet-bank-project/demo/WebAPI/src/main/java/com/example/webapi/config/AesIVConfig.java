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
public class AesIVConfig {

    @Value("${aes.iv.file.path}")
    private String aesIVFilePath;

    @PostConstruct
    public void generateAndSaveAESIV() {
        // Kiểm tra xem đã tạo IV hay chưa, nếu đã tạo thì không cần tạo lại
        if (isAESIVExist()) {
            return;
        }

        try {
            // Tạo IV ngẫu nhiên
            byte[] aesIV = generateRandomAESIV();

//             Chuyển đổi IV thành chuỗi hex
            String hexAESIV = convertAESIVToHex(aesIV);

//             Lưu chuỗi hex vào file
            saveHexAESIVToFile(hexAESIV);

            System.out.println("Random AES IV key generated and saved to files.");
            System.out.println("AES IV key is: " + hexAESIV);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tạo hoặc lưu trữ IV.");
        }
    }

    private byte[] generateRandomAESIV() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] aesIV = new byte[16]; // Độ dài IV là 128 bit (16 bytes)
        secureRandom.nextBytes(aesIV);
        return aesIV;
    }

    private String convertAESIVToHex(byte[] aesIV) {
        return Hex.encodeHexString(aesIV);
    }

    private void saveHexAESIVToFile(String hexAESIV) throws IOException {
        File aesIVFile = new File(aesIVFilePath);
        hexAESIV=hexAESIV.substring(0, 16);
        FileOutputStream fos = new FileOutputStream(aesIVFile);
        fos.write(hexAESIV.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }


    private boolean isAESIVExist() {
        File aesIVFile = new File(aesIVFilePath);
        return aesIVFile.exists();
    }
}