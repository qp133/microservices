package com.example.webapi.bank.impl;

import com.example.webapi.bank.BankService;
import com.example.webapi.dtos.request.*;
import com.example.webapi.dtos.response.DataDepositResponse;
import com.example.webapi.dtos.response.DataLinkResponse;
import com.example.webapi.dtos.response.ResponseTemp;
import com.example.webapi.dtos.response.WalletResponse;
import com.example.webapi.entity.Card;
import com.example.webapi.entity.User;
import com.example.webapi.exception.NotFoundException;
import com.example.webapi.repository.CardRepository;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.service.AuthenService;
import com.example.webapi.util.AesUtils;
import com.example.webapi.util.RandomUtils;
import com.example.webapi.util.RsaUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;

import static com.example.webapi.constants.AES_KEY;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    AuthenService authenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> link(String token, WalletRequest walletRequest, DataLinkRequest dataLinkRequest) {
//        ResponseTemp res = ResponseTemp.builder()
//                .res_code(new ResponseTemp.ResCode())
//                .data(WalletResponse.builder()
//                        .msg_id(dataAfterSign.getMsgId())
//                        .data(dataAfterSign.getDataRequest())
//                        .signature(dataAfterSign.getSignature())
//                        .exchange_key(dataAfterSign.getExchangeKey())
//                        .time(dataAfterSign.getTime())
//                        .build())
//                .build();

        String clientId = RandomUtils.generateRandomString(48);
        String clientSecret = RandomUtils.generateRandomString(57);

        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(DataLinkResponse.builder()
                        .client_id(clientId)
                        .client_secret(clientSecret)
                        .unique_id_name(dataLinkRequest.getUnique_id_name())
                        .unique_id_value(dataLinkRequest.getUnique_id_value())
                        .phone_number(dataLinkRequest.getPhone_number())
                        .full_name(dataLinkRequest.getFull_name())
                        .customer_type(dataLinkRequest.getCustomer_type())
                        .customer_no(dataLinkRequest.getCustomer_no())
                        .account_no(dataLinkRequest.getAccount_no())
                        .card_info(dataLinkRequest.getCard_info())
                        .build())
                .build();

        //Tìm user theo token. Set clientId và clientSecret cho user
        User user = authenService.getUserFromToken(token);
        String email = user.getEmail();

        User userDB = userRepository.findByEmail(email);
        if(userDB.getClientId().isEmpty()) {
            userDB.setClientId(clientId);
        }
        if (userDB.getClientSecret().isEmpty()) {
            userDB.setClientSecret(clientSecret);
        }
        userRepository.save(userDB);

        return decrypt(walletRequest);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> otp(WalletRequest walletRequest, DataOtpRequest dataOtpRequest) {
        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(DataOtpRequest.builder()
                        .client_id(dataOtpRequest.getClient_id())
                        .client_secret(dataOtpRequest.getClient_secret())
                        .otp(dataOtpRequest.getOtp())
                        .build())
                .build();

        DataOtpRequest resData = (DataOtpRequest) res.getData();
        if (!dataOtpRequest.getOtp().equals(resData.getOtp())) {
            return (ResponseEntity<WalletResponse>) ResponseEntity.badRequest();
        }

        return decrypt(walletRequest);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> unlink(WalletRequest walletRequest, DataUnlinkRequest dataUnlinkRequest) {
        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(new DataUnlinkRequest())
                .build();

        //dựa vào client id, client secret tìm ra thông tin card. Xóa card
        String clientId = dataUnlinkRequest.getClient_id();
        User user = userRepository.findByClientId(clientId);
        int userId = user.getId();
        Card card = cardRepository.findByUser_Id(userId);
        cardRepository.delete(card);

        return decrypt(walletRequest);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> deposit(WalletRequest walletRequest, DataDepositRequest dataDepositRequest) {
        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(DataDepositResponse.builder()
                        .transaction_id(RandomUtils.generateRandomString(10))
                        .ref_no(dataDepositRequest.getRef_no())
                        .amount(dataDepositRequest.getAmount())
                        .type("DEPOSIT")
                        .description(dataDepositRequest.getDescription())
                        .date_time(dataDepositRequest.getDate_time())
                        .build())
                .build();

        //Tìm user theo clientId. Tìm card theo userId của user. Set lại số dư
        String clientId = dataDepositRequest.getClient_id();
        User user = userRepository.findByClientId(clientId);
        int userId = user.getId();
        Card card = cardRepository.findByUser_Id(userId);
        if (card.getAmount() == null) {
            card.setAmount(0);
        }
        Integer amount = card.getAmount() + dataDepositRequest.getAmount();
        card.setAmount(amount);
        cardRepository.save(card);

        return decrypt(walletRequest);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<WalletResponse> withdraw(WalletRequest walletRequest, DataDepositRequest dataWithdrawRequest) {
        ResponseTemp res = ResponseTemp.builder()
                .res_code(new ResponseTemp.ResCode())
                .data(DataDepositResponse.builder()
                        .transaction_id(RandomUtils.generateRandomString(10))
                        .ref_no(dataWithdrawRequest.getRef_no())
                        .amount(dataWithdrawRequest.getAmount())
                        .type("WITHDRAW")
                        .description(dataWithdrawRequest.getDescription())
                        .date_time(dataWithdrawRequest.getDate_time())
                        .build())
                .build();

        //Tìm user theo clientId. Tìm card theo userId của user. Set lại số dư
        String clientId = dataWithdrawRequest.getClient_id();
        User user = userRepository.findByClientId(clientId);
        int userId = user.getId();
        Card card = cardRepository.findByUser_Id(userId);
        if (card.getAmount() == null) {
            card.setAmount(0);
        }
        int amount = card.getAmount() - dataWithdrawRequest.getAmount();
        if(amount < 0 || card.getAmount() < dataWithdrawRequest.getAmount()) {
            throw new NotFoundException("Số dư không đủ để rút tiền");
        }
        card.setAmount(amount);
        cardRepository.save(card);

        return decrypt(walletRequest);
    }

    //---------------------- LOGIC ----------------------
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
//        headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9." +
//                "eyJzdWIiOiJWTlBPU1RfQ1RWIiwidXNlciI6IlZOUE9TVF9DVFYiLCJhdXRob3JpdGllcyI6WyJST0x" +
//                "FX0FETUlOIl0sInNjYW4taXAiOiJWTlBPU1RfU0NBTiIsImZyb20taXAiOiIxMDMuNjMuMTExLjE2NCIsInR" +
//                "vLWlwIjoiMTAzLjYzLjExMS4xNjUiLCJpc3MiOiJvcGVuLWJhbmtpbmctYXV0aC1zZXJ2aWNlIiwiaWF0IjoxN" +
//                "jg5MjE0Nzk2LCJleHAiOjE2ODkzMDExOTZ9.UA4zurzT0XEeTNAf3A63Ux__jXXZhPl6jcTjFtY7KIU");
        return headers;
    }

    private ResponseEntity<WalletResponse>  decrypt(WalletRequest walletRequest) throws Exception {
        WalletRequest dataAfterSign = walletRequest.sign();

        //gửi request sang module Gateway
        //Gateway gửi request sang module phía ngân hàng và nhận về response, gửi lại response về đây

        //Gửi request và nhận về response
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity requestEntity = new HttpEntity(dataAfterSign, getDefaultHeaders());
//        ResponseEntity<ResponseTemp> res = restTemplate.exchange("http://202.134.17.221:9091/gw/puclic/open-banking-service/api/v1/ewallet/link", HttpMethod.POST, requestEntity, ResponseTemp.class);

        //Xử lý response trả về
//        WalletResponse walletResponse = Objects.requireNonNull(res.getBody()).getData();
//        WalletResponse walletResponse = (WalletResponse) Objects.requireNonNull(res.getData());

        WalletResponse walletResponse = WalletResponse.builder()
                .msg_id(dataAfterSign.getMsgId())
                .data(dataAfterSign.getDataRequest())
                .signature(dataAfterSign.getSignature())
                .exchange_key(dataAfterSign.getExchangeKey())
                .time(dataAfterSign.getTime())
                .build();

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
//        DataLinkResponse dataLinkResponse = new ObjectMapper().readValue(decryptedData, DataLinkResponse.class);

        // Kiểm tra chữ ký
        boolean isValidSignature = verifySignature(decryptedData, AES_KEY, signature );

        // Xử lý dữ liệu và tạo phản hồi
        if (isValidSignature) {
            return ResponseEntity.ok(walletResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
