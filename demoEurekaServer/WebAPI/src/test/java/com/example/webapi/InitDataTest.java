package com.example.webapi;

import com.example.webapi.entity.Role;
import com.example.webapi.entity.User;
import com.example.webapi.repository.RoleRepository;
import com.example.webapi.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootTest
public class InitDataTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Faker faker;

    @Test
    void save_role() {
        String[] roles = {"ADMIN", "USER", "GUEST"};
        for (int i = 0; i < 3; i++) {
            Role role = Role.builder()
                    .role(roles[i])
                    .build();
            roleRepository.save(role);
        }
    }

    @Test
    void save_user() {
        Random rd = new Random();

        List<Role> roles = roleRepository.findAll();

        for (int i = 0; i < 10; i++) {
            //Random 1 ds roles
            Set<Role> rdListRoles = new HashSet<>();
            for (int j = 0; j < 1; j++) {
                Role rdRole = roles.get(rd.nextInt(roles.size()));
                rdListRoles.add(rdRole);
            }

            User user = User.builder()
                    .name(faker.name().fullName())
                    .email(faker.internet().emailAddress())
                    .password("111")
                    .roles(rdListRoles)
                    .build();
            userRepository.save(user);
        }
    }
}
