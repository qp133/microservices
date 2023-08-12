package com.example.webapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCardRequest {
    private String cardNumber;
    private String cardHolderName;
    private String cardExp;
    private String cardCcv;
}
