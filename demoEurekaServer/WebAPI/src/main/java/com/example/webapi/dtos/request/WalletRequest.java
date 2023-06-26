package com.example.webapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {
    private String msgId;
    private DataRequest dataRequest;
    private String signature;
    private String exchangeKey;
    private LocalDateTime time;
}
