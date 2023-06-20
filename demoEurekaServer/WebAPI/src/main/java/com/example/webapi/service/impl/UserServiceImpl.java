package com.example.webapi.service.impl;

import com.example.webapi.dtos.response.UserResponse;
import com.example.webapi.entity.Role;
import com.example.webapi.repository.RoleRepository;
import com.example.webapi.service.UserService;
import com.example.webapi.entity.User;
import com.example.webapi.exception.BadRequestException;
import com.example.webapi.exception.NotFoundException;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.dtos.request.CreateAccountRequest;
import com.example.webapi.dtos.request.UpdatePasswordRequest;
import com.example.webapi.dtos.request.UpsertUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());

            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        return userResponse;
    }

    @Override
    public UserResponse createUser(UpsertUserRequest request) {
        //Lấy ra danh sách roles tương ứng (từ ds id gửi lên)
        Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());

        boolean checkUser = userRepository.existsByNameAllIgnoreCase(request.getName());
        if (checkUser) {
            throw new BadRequestException("User " + request.getName() + " has already exist.");
        }
        boolean checkEmail=userRepository.existsByEmailAllIgnoreCase(request.getEmail());
        if (checkEmail) {
            throw new BadRequestException("Email " + request.getEmail() + " has already exist.");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(roles)
                .build();
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());

        return userResponse;
    }

    @Override
    public UserResponse updateUser(UpsertUserRequest request, Integer id) {
        //Lấy ra danh sách roles tương ứng (từ ds id gửi lên)
        Set<Role> roles = roleRepository.findByIdIn(request.getRoleIds());

        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        boolean checkUser = userRepository.existsByNameAllIgnoreCase(request.getName());
        if (checkUser) {
            throw new BadRequestException("User " + request.getName() + " has already exist.");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRoles(roles);

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        userRepository.delete(user);
    }

    @Override
    public void updatePassword(Integer id, UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // Kiểm tra xem password cũ có chính xác hay không
        if(!user.getPassword().equals(request.getOldPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        // Kiểm tra xem password cũ và mới có trùng nhau hay không
        if(request.getOldPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("Mật khẩu cũ và mới không được trùng nhau");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public String forgotPassword(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // Generate password
        String newPassword = UUID.randomUUID().toString();

        user.setPassword(newPassword);
        userRepository.save(user);

        return newPassword;
    }

    @Override
    public UserResponse createAccount(CreateAccountRequest createAccountRequest) {
        boolean checkUser = userRepository.existsByNameAllIgnoreCase(createAccountRequest.getName());
        if (checkUser) {
            throw new BadRequestException("User " + createAccountRequest.getName() + " has already exist.");
        }
        boolean checkEmail=userRepository.existsByEmailAllIgnoreCase(createAccountRequest.getEmail());
        if (checkEmail) {
            throw new BadRequestException("Email " + createAccountRequest.getEmail() + " has already exist.");
        }
        if (!Objects.equals(createAccountRequest.getPassword(), createAccountRequest.getRepeatPassword())){
            throw new BadRequestException("Password and repeat password is not match");
        }
        if(createAccountRequest.getName().equals("")||createAccountRequest.getEmail().equals("")||createAccountRequest.getPassword().equals("")||createAccountRequest.getRepeatPassword().equals("")){
            throw new BadRequestException("Không được để trống thông tin");
        }
        User user = User.builder()
                .name(createAccountRequest.getName())
                .email(createAccountRequest.getEmail())
                .password(createAccountRequest.getPassword())
                .roles(Set.of())
                .build();
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());

        return userResponse;
    }
}
