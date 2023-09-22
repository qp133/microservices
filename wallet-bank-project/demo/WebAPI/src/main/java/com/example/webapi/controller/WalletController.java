package com.example.webapi.controller;

import com.example.webapi.dtos.request.*;
import com.example.webapi.dtos.response.WalletResponse;
import com.example.webapi.service.AuthenService;
import com.example.webapi.service.WalletService;
import com.example.webapi.service.impl.DataLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gw/internal/open-banking-service/api/v1/ewallet/")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    DataLinkService dataLinkService;

    @PostMapping("link")
    public ResponseEntity<WalletResponse> walletLink(@RequestHeader("Authorization") String authorization, @RequestBody UpsertCardRequest upsertCardRequest) throws Exception{
        String token = authorization.replace("Bearer ", "");
        authenService.verifyAuthenticate(token);

        DataLinkRequest dataLinkRequest = dataLinkService.parseUserFromToken(token, upsertCardRequest);

        return walletService.walletLink(token, dataLinkRequest);
    }

    @PostMapping("verify-otp")
    public ResponseEntity<WalletResponse> walletOtp(@RequestHeader("Authorization") String authorization,@RequestParam String otp) throws Exception {
        String token = authorization.replace("Bearer ", "");
        authenService.verifyAuthenticate(token);

        DataOtpRequest dataOtpRequest = dataLinkService.parseUserFromToken(token, otp);

        return walletService.walletOtp(dataOtpRequest);
    }

    @PostMapping("unlink")
    public ResponseEntity<WalletResponse> walletUnLink(@RequestHeader("Authorization") String authorization) throws Exception {
        String token = authorization.replace("Bearer ", "");
        authenService.verifyAuthenticate(token);

        DataUnlinkRequest dataUnlinkRequest = dataLinkService.parseUserFromToken(token);

        return walletService.walletUnLink(dataUnlinkRequest);
    }

    @PostMapping("deposit")
    public ResponseEntity<WalletResponse> walletDeposit(@RequestHeader("Authorization") String authorization, @RequestBody UpsertDepositRequest upsertDepositRequest) throws Exception{
        String token = authorization.replace("Bearer ", "");
        authenService.verifyAuthenticate(token);

        DataDepositRequest dataDepositRequest = dataLinkService.parseUserFromToken(token, upsertDepositRequest);

        return walletService.walletDeposit(dataDepositRequest);
    }

    @PostMapping("withdraw")
    public ResponseEntity<WalletResponse> walletWithdraw(@RequestHeader("Authorization") String authorization, @RequestBody UpsertDepositRequest upsertDepositRequest) throws Exception {
        String token = authorization.replace("Bearer ", "");
        authenService.verifyAuthenticate(token);

        DataDepositRequest dataWithdrawRequest = dataLinkService.parseUserFromToken(token, upsertDepositRequest);

        return walletService.walletWithdraw(dataWithdrawRequest);
    }

}
