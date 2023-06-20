package com.example.webapi.client;

import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;

public class GatewayFeignClient {
    private GatewayClient client;

    public GatewayFeignClient() {
        this.client = Feign.builder()
                .logger(new Slf4jLogger(GatewayClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GatewayClient.class, "http://localhost:8082");
    }

    public String getResource() {
        return client.getResource();
    }
}