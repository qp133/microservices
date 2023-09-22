package com.example.webapi.service.impl;

import com.example.webapi.bank.BankService;
import com.example.webapi.dtos.request.*;
import com.example.webapi.dtos.response.WalletResponse;
import com.example.webapi.service.WalletService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    BankService bankService;

    static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public ResponseEntity<WalletResponse> walletLink(String token, DataLinkRequest dataLinkRequest) throws  Exception{
        //map DataLinkRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataLinkRequest)).build();

        return bankService.link(token, walletRequest, dataLinkRequest);
    }

    @Override
    public ResponseEntity<WalletResponse> walletOtp(DataOtpRequest dataOtpRequest) throws Exception {
        //map DataOtpRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataOtpRequest)).build();

        return bankService.otp(walletRequest, dataOtpRequest);
    }

    @Override
    public ResponseEntity<WalletResponse> walletUnLink(DataUnlinkRequest dataUnlinkRequest) throws Exception {
        //map DataUnlinkRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataUnlinkRequest)).build();

        return bankService.unlink(walletRequest, dataUnlinkRequest);
    }

    @Override
    public ResponseEntity<WalletResponse> walletDeposit(DataDepositRequest dataDepositRequest) throws Exception{
        //map DataDepositRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataDepositRequest)).build();

        return bankService.deposit(walletRequest, dataDepositRequest);
    }

    @Override
    public ResponseEntity<WalletResponse> walletWithdraw(DataDepositRequest dataWithdrawRequest) throws Exception {
        //map DataWithdrawRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataWithdrawRequest)).build();

        return bankService.withdraw(walletRequest, dataWithdrawRequest);    }

}
