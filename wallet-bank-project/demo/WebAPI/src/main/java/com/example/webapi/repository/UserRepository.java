package com.example.webapi.repository;

import com.example.webapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailAllIgnoreCase(String email);

    boolean existsByFullNameAllIgnoreCase(String name);

    User findByEmail(String email);

    User findByClientId(String clientId);

}