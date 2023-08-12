package com.example.webapi.controller;

import com.example.webapi.dtos.request.DataLinkRequest;
import com.example.webapi.dtos.request.WalletRequest;
import com.example.webapi.dtos.response.WalletResponse;
import com.example.webapi.service.AuthenService;
import com.example.webapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gw/internal/open-banking-service/api/v1/ewallet/")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private AuthenService authenService;

    @PostMapping("link")
    public ResponseEntity<WalletResponse> walletLink(@RequestParam("Authorization") String token, @RequestBody DataLinkRequest dataLinkRequest) throws Exception{
        String verify = authenService.verifyAuthenticate(token);

        ResponseEntity<WalletResponse> res = walletService.walletLink(dataLinkRequest);
        return res;
    }

    @PostMapping("verify-otp")
    public WalletResponse walletOtp(@RequestBody WalletRequest walletRequest) {
        return walletService.walletOtp(walletRequest);
    }

    @PostMapping("unlink")
    public WalletResponse walletUnLink(@RequestBody WalletRequest walletRequest) {
        return walletService.walletUnLink(walletRequest);
    }

    @PostMapping("deposit")
    public WalletResponse walletDeposit(@RequestBody WalletRequest walletRequest) {
        return walletService.walletDeposit(walletRequest);
    }

    @PostMapping("withdraw")
    public WalletResponse walletWithdraw(@RequestBody WalletRequest walletRequest) {
        return walletService.walletWithdraw(walletRequest);
    }

}
