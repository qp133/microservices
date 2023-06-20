package com.example.gatewayapi.controller;

import com.example.gatewayapi.client.WebApiFeignClient;
import com.example.gatewayapi.dtos.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    private WebApiFeignClient webApiFeignClient;

    @GetMapping("/admin/users")
    public List<UserResponse> getUsers() {
        return webApiFeignClient.getUsers();
    }

    @GetMapping("/admin/users/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return webApiFeignClient.getUserById(id);
    }
}
