package com.example.webapi.service.impl;

import com.example.webapi.dtos.request.*;
import com.example.webapi.entity.Card;
import com.example.webapi.entity.User;
import com.example.webapi.exception.BadRequestException;
import com.example.webapi.repository.CardRepository;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.service.AuthenService;
import com.example.webapi.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataLinkService {
    @Autowired
    AuthenService authenService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    public DataLinkRequest parseUserFromToken(String token, UpsertCardRequest upsertCardRequest) throws Exception{
        User user = authenService.getUserFromToken(token);

        String email = user.getEmail();
        User userDB = userRepository.findByEmail(email);

        Card card = new Card();
        card.setCard_number(upsertCardRequest.getCard_number());
        card.setCard_holder_name(upsertCardRequest.getCard_holder_name());
        card.setCard_exp(upsertCardRequest.getCard_exp());
        card.setCard_ccv(upsertCardRequest.getCard_ccv());
        card.setUser(userDB);
        cardRepository.save(card);

        return DataLinkRequest.builder()
                .unique_id_name(user.getUniqueIdName())
                .unique_id_value(user.getUniqueIdValue())
                .phone_number(user.getPhoneNumber())
                .full_name(user.getFullName())
                .customer_type(user.getCustomerType())
                .customer_no(user.getCustomerNo())
                .account_no(user.getAccountNo())
                .card_info(card)
                .build();
    }

    public DataOtpRequest parseUserFromToken(String token, String otp) throws Exception{
        User user = authenService.getUserFromToken(token);
        return DataOtpRequest.builder()
                .client_id(user.getClientId())
                .client_secret(user.getClientSecret())
                .otp(otp)
                .build();
    }

    public DataUnlinkRequest parseUserFromToken(String token) throws Exception{
        User user = authenService.getUserFromToken(token);

        String email = user.getEmail();
        User userDB = userRepository.findByEmail(email);

        return DataUnlinkRequest.builder()
                .client_id(userDB.getClientId())
                .client_secret(userDB.getClientSecret())
                .build();
    }

    public DataDepositRequest parseUserFromToken(String token, UpsertDepositRequest upsertDepositRequest) throws Exception{
        User user = authenService.getUserFromToken(token);

        String email = user.getEmail();
        User userDB = userRepository.findByEmail(email);

        if (upsertDepositRequest.getAmount() < 50000) {
            throw new BadRequestException("Số tiền tối thiểu cần nạp/rút là 50.000 VND");
        }

        return DataDepositRequest.builder()
                .client_id(userDB.getClientId())
                .client_secret(userDB.getClientSecret())
                .ref_no(RandomUtils.generateRandomString(36))
                .amount(upsertDepositRequest.getAmount())
                .description(upsertDepositRequest.getDescription())
                .date_time(LocalDateTime.now().toString())
                .build();
    }
}
