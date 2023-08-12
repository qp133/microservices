package com.example.webapi.service.impl;

import com.example.webapi.bank.BankService;
import com.example.webapi.dtos.request.DataLinkRequest;
import com.example.webapi.dtos.request.WalletRequest;
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
    public ResponseEntity<WalletResponse> walletLink(DataLinkRequest dataLinkRequest) throws  Exception{
        //map DataLinkRequest vào WalletRequest
        WalletRequest walletRequest = WalletRequest.builder().dataRequest(objectMapper.writeValueAsString(dataLinkRequest)).build();

        ResponseEntity<WalletResponse> res = bankService.link(walletRequest);

        return res;
    }

    @Override
    public WalletResponse walletOtp(WalletRequest walletRequest) {
        return null;
    }

    @Override
    public WalletResponse walletUnLink(WalletRequest walletRequest) {
        return null;
    }

    @Override
    public WalletResponse walletDeposit(WalletRequest walletRequest) {
        return null;
    }

    @Override
    public WalletResponse walletWithdraw(WalletRequest walletRequest) {
        return null;
    }

}
