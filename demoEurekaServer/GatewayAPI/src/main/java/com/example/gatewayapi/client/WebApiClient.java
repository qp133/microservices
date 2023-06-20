package com.example.gatewayapi.client;

import com.example.gatewayapi.dtos.response.UserResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Web-Api-Services")
public interface WebApiClient {
    @RequestLine("GET /api/admin/users")
    @Headers("Content-Type: application/json")
    List<UserResponse> getUsers();

    @RequestLine("GET /api/admin/users/{id}")
    @Headers("Content-Type: application/json")
    UserResponse getUserById(@Param("id") Integer id);
}
