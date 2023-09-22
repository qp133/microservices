package com.example.webapi.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataUnlinkRequest {
    @JsonProperty(value = "client_id")
    private String client_id;

    @JsonProperty(value = "client_secret")
    private String client_secret;
}
