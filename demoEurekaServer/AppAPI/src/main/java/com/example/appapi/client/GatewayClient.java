package com.example.appapi.client;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Gateway-Api-Services")
public interface GatewayClient {
    @RequestLine("GET /api/resource")
    @Headers("Content-Type: application/json")
    String getResource();


}
