package com.example.gatewayapi.client;

import com.example.gatewayapi.dtos.request.UpsertUserRequest;
import com.example.gatewayapi.dtos.response.UserResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "web")
public interface WebApiClient {
    @RequestLine("GET /user-api/admin/users")
    @Headers("Content-Type: application/json")
    List<UserResponse> getUsers();

    @RequestLine("GET /user-api/admin/users/{id}")
    @Headers("Content-Type: application/json")
    UserResponse getUserById(@Param("id") Integer id);

    @RequestLine("POST /user-api/admin/users")
    @Headers("Content-Type: application/json")
    UserResponse createUser(@RequestBody UpsertUserRequest request);

    @RequestLine("PUT /user-api/admin/users/update/{id}")
    @Headers("Content-Type: application/json")
    UserResponse updateUser(@Param("id") Integer id, @RequestBody UpsertUserRequest request);

    @RequestLine("DELETE /user-api/admin/users/delete/{id}")
    void deleteUser(@Param("id") Integer id);
}
