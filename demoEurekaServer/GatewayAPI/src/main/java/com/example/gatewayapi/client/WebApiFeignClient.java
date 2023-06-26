package com.example.gatewayapi.client;

import com.example.gatewayapi.dtos.request.UpsertUserRequest;
import com.example.gatewayapi.dtos.response.UserResponse;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class WebApiFeignClient {
    private WebApiClient client;

    public WebApiFeignClient() {
        this.client = Feign.builder()
                .logger(new Slf4jLogger(WebApiClient.class))
                .logLevel(Logger.Level.FULL)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(WebApiClient.class, "http://localhost:8080");
    }

    public List<UserResponse> getUsers() {
        return client.getUsers();
    }

    public UserResponse getUserById(Integer id) {
        return client.getUserById(id);
    }

    public UserResponse createUser(UpsertUserRequest request) {
        return client.createUser(request);
    }

    public UserResponse updateUser(Integer id, UpsertUserRequest request) {
        return client.updateUser(id, request);
    }

    public void deleteUser(Integer id) {
        client.deleteUser(id);
    }
}
