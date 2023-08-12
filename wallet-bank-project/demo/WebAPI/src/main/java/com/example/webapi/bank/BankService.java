package com.example.webapi.bank;

import com.example.webapi.dtos.request.WalletRequest;
import com.example.webapi.dtos.response.WalletResponse;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<WalletResponse> link(WalletRequest walletRequest);
}
