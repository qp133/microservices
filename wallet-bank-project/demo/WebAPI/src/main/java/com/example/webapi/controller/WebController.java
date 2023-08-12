package com.example.webapi.controller;

import com.example.webapi.dtos.request.CreateAccountRequest;
import com.example.webapi.dtos.response.UserResponse;
import com.example.webapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web-api")
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/resource")
    public String getResource() {
        return "This is a resource from WebApi";
    }

    //create account
    @PostMapping("/handle-create-account")
    public UserResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return userService.createAccount(createAccountRequest);
    }
}
