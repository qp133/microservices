package com.example.webapi.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataDepositResponse {
    private String transaction_id;
    private String ref_no;
    private Integer amount;
    private String type;
    private String description;
    private String date_time;
}
