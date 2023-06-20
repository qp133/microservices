package com.example.webapi.service;

import com.example.webapi.entity.Card;
import com.example.webapi.dtos.request.UpsertCardRequest;

import java.util.List;

public interface CardService {
    List<Card> getAllCards();

    Card getCardById(Integer id);

    Card createCard(UpsertCardRequest request);

    Card updateCard(Integer id, UpsertCardRequest request);

    void deleteCard(Integer id);
}
