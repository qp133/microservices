package com.example.authenapi;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AuthenApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenApiApplication.class, args);
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
