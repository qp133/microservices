package com.example.authenapi.service;

import com.example.authenapi.dtos.request.LoginRequest;
import com.example.authenapi.dtos.response.AuthResponse;
import com.example.authenapi.secutity.CustomUserDetails;
import com.example.authenapi.secutity.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse login(LoginRequest request) {
        try {
            // Tạo đối tượng để xác thực
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Lưu vào trong context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String token = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return new AuthResponse(userDetails.getUser(), token, true);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email hoặc password không chính xác");
        }
    }

    public String logout(HttpSession session) {
        session.invalidate();
        return "Đăng xuất thành công";
    }

}