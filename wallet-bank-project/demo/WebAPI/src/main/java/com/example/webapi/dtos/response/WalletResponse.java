package com.example.webapi.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletResponse {
    private String msg_id;

    private String data;

    private String signature;

    private String exchange_key;

    private String time;
}
