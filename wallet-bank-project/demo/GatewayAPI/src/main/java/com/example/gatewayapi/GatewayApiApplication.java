package com.example.gatewayapi;

import com.example.gatewayapi.client.AppApiFeignClient;
import com.example.gatewayapi.client.WebApiFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApiApplication.class, args);
    }

    @Bean
    public WebApiFeignClient webApiFeignClient() {
        return new WebApiFeignClient();
    }

    @Bean
    public AppApiFeignClient appApiFeignClient() {
        return new AppApiFeignClient();
    }
}
