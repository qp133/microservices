package com.example.authenapi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @PostMapping("handle-login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/handle-logout")
    public String logout(HttpSession session) {
        return authService.logout(session);
    }

}