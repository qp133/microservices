package com.example.authenapi.controller;

import com.example.authenapi.dtos.request.LoginRequest;
import com.example.authenapi.dtos.response.AuthResponse;
import com.example.authenapi.entity.User;
import com.example.authenapi.secutity.JwtUtils;
import com.example.authenapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("handle-login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        System.out.println("Login Success - Quang");
        return authService.login(request);
    }

    @GetMapping("/handle-logout")
    public String logout(HttpSession session) {
        System.out.println("Logout Success - Quang");
        return authService.logout(session);
    }

    @GetMapping("/verify-authen")
    public String verifyAuthenticate(@RequestHeader("Authorization") String token) throws Exception {
        return jwtUtils.getAuthentication(token);
    }

    @GetMapping("/get-user")
    public User getUserFromToken(@RequestHeader("Authorization") String token) throws Exception{
        return jwtUtils.getUserFromToken(token);
    }

}