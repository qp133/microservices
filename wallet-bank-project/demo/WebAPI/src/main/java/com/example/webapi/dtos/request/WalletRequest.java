package com.example.webapi.dtos.request;

import com.example.webapi.util.AesUtils;
import com.example.webapi.util.RsaUtils;
import lombok.*;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.example.webapi.constants.AES_KEY;
import static com.example.webapi.constants.AES_KEY_2;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequest {
    private String msgId;
    private String dataRequest;
    private String signature;
    private String exchangeKey;
    private String time;

    public WalletRequest sign() throws Exception {
        String rdMsgId = generateRandomNumbers(12);
//        String rdMsgId = "230720182012";

//        //Tự sinh init vector và aes key
        String iv = AesUtils.getAesIVKey();
//        String iv = "WUge0nbdSM8YmnKU";

        String aesKey = AES_KEY;

        //Mã hóa exchange_key: là mã hoá của init vector (iv)
        //Key được encode base 64 và mã hóa theo chuẩn RSA.
        byte[] ivKeyBytes = iv.getBytes();
        String exchangeKeyBase64 = Base64.getEncoder().encodeToString(ivKeyBytes);

        //Lấy ra publicKey
        PublicKey pubKey = RsaUtils.getPublicKey();

        //Mã hóa RSA
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encryptOut = c.doFinal(exchangeKeyBase64.getBytes(StandardCharsets.UTF_8));
        String exchangeKeyEncrypted = Base64.getEncoder().encodeToString(encryptOut);
//        String exchangeKeyEncrypted = "GP1/8fDBwIiJh0htitnrXJSDZ+KdbCjwfZTtNDoJpjPxhuo2dLx0aV6bclgj7B3TWrW/KjRTgQmzv5X3wlD5fFj+Ss1o1gkJLQOgw6cHpVQ59sIeLd4OIvr3Sxte5ebP1KWnHSOXWKzoH7GdA97xRgzFn83qkRlEAbvItkXW1Q0=";


        //chuyển string[] dataRequest sang json
        String jsonData = this.getDataRequest();

        //Mã hóa data theo AES cần: string data + key + init vector
        String AesEncrypted = AesUtils.encryptAES(jsonData, aesKey, iv);

        //Mã hóa signature (là mã hoá của chuỗi Json_data + AES_key) theo SHA1
        String time = LocalDateTime.now().toString();
//        String time = "2023-07-21 17:03:45";

        String inputToHash = jsonData + aesKey;
        String signatureHashedString = signatureHashStringToBase64(inputToHash);
//        String signatureHashedString = "2940e9adee73009dd6ff1398fcb9bef156b7e6f4";

        //Lưu thông tin vào WalletRequest
        this.setMsgId(rdMsgId);
        this.setDataRequest(AesEncrypted);
        this.setExchangeKey(exchangeKeyEncrypted);
        this.setSignature(signatureHashedString);
        this.setTime(time);

        return this;
    }

    public static String generateRandomNumbers(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNumber = (int) (Math.random() * 10);
            sb.append(randomNumber);
        }
        return sb.toString();
    }

    public String signatureHashStringToBase64(String input) {
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes(StandardCharsets.UTF_8), 0, input.length());
            byte[] digest = msdDigest.digest();
            return DatatypeConverter.printHexBinary(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
