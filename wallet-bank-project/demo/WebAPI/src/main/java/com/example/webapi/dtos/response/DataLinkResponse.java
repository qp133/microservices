package com.example.webapi.dtos.response;

import com.example.webapi.entity.Card;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataLinkResponse {
    private String client_id;
    private String client_secret;
    private String unique_id_name;
    private String unique_id_value;
    private String phone_number;
    private String full_name;
    private String customer_type;
    private String customer_no;
    private String account_no;
    private Card card_info;
}
