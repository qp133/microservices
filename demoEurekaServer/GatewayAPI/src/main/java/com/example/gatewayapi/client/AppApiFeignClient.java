package com.example.gatewayapi.client;

import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;

public class AppApiFeignClient {
    private AppApiClient client;

    public AppApiFeignClient() {
        this.client = Feign.builder()
                .logger(new Slf4jLogger(AppApiClient.class))
                .logLevel(Logger.Level.FULL)
                .target(AppApiClient.class, "http://localhost:8080");
    }

    public String getResource() {
        return client.getResource();
    }
}
