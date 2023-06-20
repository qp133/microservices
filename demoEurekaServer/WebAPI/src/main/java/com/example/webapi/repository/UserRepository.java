package com.example.webapi.repository;

import com.example.webapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailAllIgnoreCase(String email);

    boolean existsByNameAllIgnoreCase(String name);

    Optional<User> findByEmail(String email);
}