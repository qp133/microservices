package com.example.gatewayapi.controller;

import com.example.gatewayapi.client.WebApiFeignClient;
import com.example.gatewayapi.dtos.request.UpsertUserRequest;
import com.example.gatewayapi.dtos.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-api")
public class UserController {
    @Autowired
    private WebApiFeignClient webApiFeignClient;

    @GetMapping("/admin/users")
    public List<UserResponse> getUsers() {
        return webApiFeignClient.getUsers();
    }

    @GetMapping("/admin/users/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return webApiFeignClient.getUserById(id);
    }

    @PostMapping("/admin/users")
    public UserResponse createUser(@RequestBody UpsertUserRequest request) {
        return webApiFeignClient.createUser(request);
    }

    @PutMapping("/admin/users/update/{id}")
    public UserResponse updateUser(@PathVariable Integer id, @RequestBody UpsertUserRequest request) {
        return webApiFeignClient.updateUser(id,request);
    }

    @DeleteMapping("admin/users/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        webApiFeignClient.deleteUser(id);
    }
}
