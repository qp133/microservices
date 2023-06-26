package com.example.authenapi.repository;

import com.example.authenapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByIdIn(List<Integer> ids);
}