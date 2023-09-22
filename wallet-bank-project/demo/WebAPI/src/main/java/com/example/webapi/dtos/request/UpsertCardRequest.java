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
    private String card_number;
    private String card_holder_name;
    private String card_exp;
    private String card_ccv;
}
