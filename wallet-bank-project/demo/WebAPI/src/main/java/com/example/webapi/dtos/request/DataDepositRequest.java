package com.example.webapi.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDepositRequest {
    @JsonProperty(value = "client_id")
    private String client_id;

    @JsonProperty(value = "client_secret")
    private String client_secret;

    @JsonProperty(value = "ref_no")
    private String ref_no;

    @JsonProperty(value = "amount")
    private Integer amount;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "date_time")
    private String date_time;
}
