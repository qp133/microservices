package com.example.webapi.repository;

import com.example.webapi.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findByUser_Id(Integer id);

}