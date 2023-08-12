package com.example.webapi.service;

import com.example.webapi.dtos.request.CreateAccountRequest;
import com.example.webapi.dtos.request.UpdatePasswordRequest;
import com.example.webapi.dtos.request.UpsertUserRequest;
import com.example.webapi.dtos.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse getUserById(Integer id);

    UserResponse createUser(UpsertUserRequest request);

    UserResponse updateUser(UpsertUserRequest request, Integer id);

    void deleteUser(Integer id);

    void updatePassword(Integer id, UpdatePasswordRequest request);

    String forgotPassword(Integer id);

    UserResponse createAccount(CreateAccountRequest createAccountRequest);
}
