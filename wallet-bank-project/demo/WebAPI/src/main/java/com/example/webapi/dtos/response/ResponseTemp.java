package com.example.webapi.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTemp<E> {
    @Data
    public static class ResCode {
        private String error_code;
        private String error_desc;
        private String ref_code;
        private String ref_desc;
    }
    @JsonProperty(value = "res_code")
    private ResCode res_code;

    @JsonProperty(value = "data")
    private E data;
}
