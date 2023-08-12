package com.example.webapi.dtos.request;

import com.example.webapi.entity.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataLinkRequest {
    @JsonProperty(value = "unique_id_name")
    private String unique_id_name;

    @JsonProperty(value = "unique_id_value")
    private String unique_id_value;

    @JsonProperty(value = "phone_number")
    private String phone_number;

    @JsonProperty(value = "full_name")
    private String full_name;

    @JsonProperty(value = "customer_type")
    private String customer_type;

    @JsonProperty(value = "customer_no")
    private String customer_no;

    @JsonProperty(value = "account_no")
    private String account_no;

    @JsonProperty(value = "card_info")
    private Card card_info;
}
