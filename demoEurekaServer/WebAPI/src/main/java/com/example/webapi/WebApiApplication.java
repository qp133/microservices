package com.example.webapi;

import com.example.webapi.client.GatewayFeignClient;
import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @Bean
    public GatewayFeignClient gatewayFeignClient() {
        return new GatewayFeignClient();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
