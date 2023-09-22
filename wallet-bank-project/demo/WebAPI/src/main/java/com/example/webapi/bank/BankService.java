package com.example.webapi.bank;

import com.example.webapi.dtos.request.*;
import com.example.webapi.dtos.response.WalletResponse;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<WalletResponse> link(String token, WalletRequest walletRequest, DataLinkRequest dataLinkRequest);

    ResponseEntity<WalletResponse> otp(WalletRequest walletRequest, DataOtpRequest dataOtpRequest);

    ResponseEntity<WalletResponse> unlink(WalletRequest walletRequest, DataUnlinkRequest dataUnlinkRequest);

    ResponseEntity<WalletResponse> deposit(WalletRequest walletRequest, DataDepositRequest dataDepositRequest);

    ResponseEntity<WalletResponse> withdraw(WalletRequest walletRequest, DataDepositRequest dataWithdrawRequest);

}
