package com.example.gatewayapi.client;

import com.example.gatewayapi.dtos.request.UpsertUserRequest;
import com.example.gatewayapi.dtos.response.UserResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "web-api-services")
public interface WebApiClient {
    @RequestLine("GET /api/admin/users")
    @Headers("Content-Type: application/json")
    List<UserResponse> getUsers();

    @RequestLine("GET /api/admin/users/{id}")
    @Headers("Content-Type: application/json")
    UserResponse getUserById(@Param("id") Integer id);

    @RequestLine("POST /api/admin/users")
    @Headers("Content-Type: application/json")
    UserResponse createUser(@RequestBody UpsertUserRequest request);

    @RequestLine("PUT /api/admin/users/update/{id}")
    @Headers("Content-Type: application/json")
    UserResponse updateUser(@Param("id") Integer id, @RequestBody UpsertUserRequest request);

    @RequestLine("DELETE /api/admin/users/delete/{id}")
    void deleteUser(@Param("id") Integer id);
}
