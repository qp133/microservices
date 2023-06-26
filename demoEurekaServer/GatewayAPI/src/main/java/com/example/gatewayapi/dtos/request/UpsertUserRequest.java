package com.example.gatewayapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {
    private String name;
    private String email;
    private String password;
    private List<Integer> roleIds;
}