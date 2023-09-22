package com.example.webapi.service;

import com.example.webapi.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authen")
@Service
public interface AuthenService {
    @GetMapping("api/auth/verify-authen")
//    @Headers("Content-Type: application/json")
    String verifyAuthenticate(@RequestHeader("Authorization") String token) throws Exception;

    @GetMapping("api/auth/get-user")
    User getUserFromToken(@RequestHeader("Authorization") String token) throws Exception;
}
