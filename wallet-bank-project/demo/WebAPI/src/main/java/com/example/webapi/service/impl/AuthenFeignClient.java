package com.example.webapi.service.impl;

import com.example.webapi.service.AuthenService;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Component;

@Component
public class AuthenFeignClient {
    private AuthenService client;

    public AuthenFeignClient() {
        this.client = Feign.builder()
                .logger(new Slf4jLogger(AuthenService.class))
                .logLevel(Logger.Level.FULL)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(AuthenService.class, "http://localhost:8083");
    }

//    public String verifyAuthenticate(String token) throws Exception {
//        return client.verifyAuthenticate(token);
//    }
}
