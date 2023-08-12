package com.example.webapi.service;

import com.example.webapi.dtos.request.DataLinkRequest;
import com.example.webapi.dtos.request.WalletRequest;
import com.example.webapi.dtos.response.WalletResponse;
import org.springframework.http.ResponseEntity;

public interface WalletService {
    ResponseEntity<WalletResponse> walletLink(DataLinkRequest dataLinkRequest) throws Exception;
    WalletResponse walletOtp(WalletRequest walletRequest);

    WalletResponse walletUnLink(WalletRequest walletRequest);

    WalletResponse walletDeposit(WalletRequest walletRequest);

    WalletResponse walletWithdraw(WalletRequest walletRequest);
}
