package com.example.gatewayapi.dtos.request;

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
    private String serialNumber;
    private Boolean enableStatus;
}
