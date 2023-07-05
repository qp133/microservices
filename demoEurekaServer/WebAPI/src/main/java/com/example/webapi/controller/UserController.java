package com.example.webapi.controller;


import com.example.webapi.dtos.response.UserResponse;
import com.example.webapi.dtos.request.UpdatePasswordRequest;
import com.example.webapi.dtos.request.UpsertUserRequest;
import com.example.webapi.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "user-api")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    //1. Lấy danh sách user
    @GetMapping("admin/users")
    public List<UserResponse> getUsers() {
        logger.info("Received request to /users-api/admin/users my-endpoint");

        return userService.getUsers();
    }

    //2. Lấy cụ thể user theo id
    @GetMapping("admin/users/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    //3. Tạo mới user
    @PostMapping("admin/users")
    public UserResponse createUser(@RequestBody UpsertUserRequest request) {
        return userService.createUser(request);
    }

    //4. Cập nhật user
    @PutMapping("admin/users/update/{id}")
    public UserResponse updateUser(@RequestBody UpsertUserRequest request, @PathVariable Integer id) {
        return userService.updateUser(request, id);
    }

    //5. Xóa user
    @DeleteMapping("admin/users/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // Update password
    @PutMapping("/users/{id}/update-password")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void updatePassword(@PathVariable Integer id, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
    }

    // Quên mật khẩu
    @PostMapping("/users/{id}/forgot-password")
    public String forgotPassword(@PathVariable Integer id) {
        return userService.forgotPassword(id);
    }

}
