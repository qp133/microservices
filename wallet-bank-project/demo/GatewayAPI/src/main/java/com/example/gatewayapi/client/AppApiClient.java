package com.example.gatewayapi.client;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "App-Api-Services")
public interface AppApiClient {
    @RequestLine("GET /api/resource")
    @Headers("Content-Type: application/json")
    String getResource();
}