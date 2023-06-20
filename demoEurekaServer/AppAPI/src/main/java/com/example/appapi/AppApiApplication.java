package com.example.appapi;

import com.example.appapi.client.GatewayFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApiApplication.class, args);
    }

    @Bean
    public GatewayFeignClient gatewayFeignClient() {
        return new GatewayFeignClient();
    }
}
