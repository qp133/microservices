package com.example.webapi.service.impl;

import com.example.webapi.service.CardService;
import com.example.webapi.entity.Card;
import com.example.webapi.exception.NotFoundException;
import com.example.webapi.repository.CardRepository;
import com.example.webapi.dtos.request.UpsertCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card getCardById(Integer id) {
        return cardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found card with id = " + id);
        });
    }

    @Override
    public Card createCard(UpsertCardRequest request) {
        Card card = Card.builder()
                .cardNumber(request.getCardNumber())
                .serialNumber(request.getSerialNumber())
                .enableStatus(request.getEnableStatus())
                .build();
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(Integer id, UpsertCardRequest request) {
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found card with id = " + id);
        });
        card.setCardNumber(request.getCardNumber());
        card.setSerialNumber(request.getSerialNumber());
        card.setEnableStatus(request.getEnableStatus());

        return cardRepository.save(card);
    }

    @Override
    public void deleteCard(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found card with id = " + id);
        });
        cardRepository.delete(card);
    }
}
