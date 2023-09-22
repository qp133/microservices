package com.example.webapi.service;

import com.example.webapi.dtos.request.*;
import com.example.webapi.dtos.response.WalletResponse;
import org.springframework.http.ResponseEntity;

public interface WalletService {
    ResponseEntity<WalletResponse> walletLink(String token, DataLinkRequest dataLinkRequest) throws Exception;
    ResponseEntity<WalletResponse> walletOtp(DataOtpRequest dataOtpRequest) throws Exception;

    ResponseEntity<WalletResponse> walletUnLink(DataUnlinkRequest dataUnlinkRequest) throws Exception;

    ResponseEntity<WalletResponse> walletDeposit(DataDepositRequest dataDepositRequest) throws Exception;

    ResponseEntity<WalletResponse> walletWithdraw(DataDepositRequest dataWithdrawRequest) throws Exception;
}
