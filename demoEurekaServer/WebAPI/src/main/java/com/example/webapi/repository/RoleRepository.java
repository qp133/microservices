package com.example.webapi.repository;

import com.example.webapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByIdIn(List<Integer> ids);
}