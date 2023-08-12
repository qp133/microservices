package com.example.webapi.bank.impl;

import com.example.webapi.bank.BankService;
import com.example.webapi.dtos.request.DataLinkRequest;
import com.example.webapi.dtos.request.WalletRequest;
import com.example.webapi.dtos.response.DataLinkResponse;
import com.example.webapi.dtos.response.ResponseTemp;
import com.example.webapi.dtos.response.WalletResponse;
import com.example.webapi.util.AesUtils;
import com.example.webapi.util.RsaUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static com.example.webapi.constants.AES_KEY;
import static com.example.webapi.constants.AES_KEY_2;

@Service
public class BankServiceImpl implements BankService {
    private static final Logger logger = LogManager.getLogger(BankServiceImpl.class);

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> link(WalletRequest walletRequest) {
        WalletRequest dataAfterSign = walletRequest.sign();

        //gửi request sang module Gateway
        //Gateway gửi request sang module phía ngân hàng và nhận về response, gửi lại response về đây

        //Gửi request và nhận về response
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity requestEntity = new HttpEntity(dataAfterSign, getDefaultHeaders());
//        ResponseEntity<ResponseTemp> res = restTemplate.exchange("http://202.134.17.221:9091/gw/puclic/open-banking-service/api/v1/ewallet/link", HttpMethod.POST, requestEntity, ResponseTemp.class);

        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(WalletResponse.builder()
                        .msg_id(dataAfterSign.getMsgId())
                        .data(dataAfterSign.getDataRequest())
                        .signature(dataAfterSign.getSignature())
                        .exchange_key(dataAfterSign.getExchangeKey())
                        .time(dataAfterSign.getTime())
                        .build())
                .build();

        //Xử lý response trả về
//        WalletResponse walletResponse = Objects.requireNonNull(res.getBody()).getData();
        WalletResponse walletResponse = Objects.requireNonNull(res.getData());

        assert walletResponse != null;

        String msgId = walletResponse.getMsg_id();
        String encryptedData = walletResponse.getData();
        String signature = walletResponse.getSignature();
        String exchangeKey = walletResponse.getExchange_key();
        String timeRes = walletResponse.getTime();

        //Giải mã exchangeKey để lấy iv
        PrivateKey privateKey = RsaUtils.getPrivateKey();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptOut = cipher.doFinal(Base64.getDecoder().decode(exchangeKey));
        String exchangeKeyDecrypted = new String(decryptOut, StandardCharsets.UTF_8);

        byte[] ivDec = Base64.getDecoder().decode(exchangeKeyDecrypted);
        String iv1 = new String(ivDec, StandardCharsets.UTF_8);
        //Giải mã data response
        String decryptedData = AesUtils.decryptAES(encryptedData, AES_KEY, iv1);

        //chuyển data response json sang Object
//        DataLinkRequest dataLinkRequest = new ObjectMapper().readValue(decryptedData, DataLinkRequest.class);
        DataLinkResponse dataLinkResponse = new ObjectMapper().readValue(decryptedData, DataLinkResponse.class);

        // Kiểm tra chữ ký
        boolean isValidSignature = verifySignature(decryptedData, AES_KEY, signature );

        // Xử lý dữ liệu và tạo phản hồi
        if (isValidSignature) {
            return ResponseEntity.ok(walletResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //---------------------- LOGIC walletLink ----------------------
    private boolean verifySignature(String decryptedData, String key, String signature ) {
        try {
            // Xác thực chữ ký bằng cách mã hóa dữ liệu và so sánh với signature

            // Mã hóa dữ liệu theo chuẩn SHA1 + aes key
            String dataToEncrypt = decryptedData + key;
            String xxx= signatureHashStringToBase64(dataToEncrypt);
            return signatureHashStringToBase64(dataToEncrypt).equals(signature);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiJWTlBPU1RfQ1RWIiwidXNlciI6IlZOUE9TVF9DVFYiLCJhdXRob3JpdGllcyI6WyJST0x" +
                "FX0FETUlOIl0sInNjYW4taXAiOiJWTlBPU1RfU0NBTiIsImZyb20taXAiOiIxMDMuNjMuMTExLjE2NCIsInR" +
                "vLWlwIjoiMTAzLjYzLjExMS4xNjUiLCJpc3MiOiJvcGVuLWJhbmtpbmctYXV0aC1zZXJ2aWNlIiwiaWF0IjoxN" +
                "jg5MjE0Nzk2LCJleHAiOjE2ODkzMDExOTZ9.UA4zurzT0XEeTNAf3A63Ux__jXXZhPl6jcTjFtY7KIU");
        return headers;
    }
}
