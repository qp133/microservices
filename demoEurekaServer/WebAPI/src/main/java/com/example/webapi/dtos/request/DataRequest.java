package com.example.webapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataRequest {
    private String clientId;
    private String clientSecret;
    private String refNo;
    private Integer amount;
    private String description;
    private LocalDateTime dateTime;
}
