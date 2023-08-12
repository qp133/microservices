package com.example.webapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "authen")
@Service
public interface AuthenService {
    @GetMapping("api/auth/verify-authen")
//    @Headers("Content-Type: application/json")
    String verifyAuthenticate(@RequestParam String token) throws Exception;
}
