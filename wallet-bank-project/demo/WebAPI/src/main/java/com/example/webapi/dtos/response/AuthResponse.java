package com.example.webapi.dtos.response;


import com.example.webapi.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse implements Serializable {
    private User user;
    private String token;
    @JsonProperty("isAuthenticated")
    private boolean isAuthenticated;
}