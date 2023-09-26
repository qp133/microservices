package com.example.webapi.service.impl;

import com.example.webapi.dtos.request.UpsertCardRequest;
import com.example.webapi.entity.Card;
import com.example.webapi.exception.NotFoundException;
import com.example.webapi.repository.CardRepository;
import com.example.webapi.service.CardService;
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
        try {
            return cardRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("Not found card with id = " + id);
        }
    }

    @Override
    public Card createCard(UpsertCardRequest request) {
        Card card = Card.builder()
                .card_number(request.getCard_number())
                .card_holder_name(request.getCard_holder_name())
                .card_exp(request.getCard_exp())
                .card_ccv(request.getCard_ccv())
                .build();
        return cardRepository.save(card);
    }

    @Override
    public Card updateCard(Integer id, UpsertCardRequest request) {
        try {
            Card card = cardRepository.findById(id).get();

            card.setCard_number(request.getCard_number());
            card.setCard_holder_name(request.getCard_holder_name());
            card.setCard_exp(request.getCard_exp());
            card.setCard_ccv(request.getCard_ccv());
            return cardRepository.save(card);
        } catch (Exception e) {
            throw new NotFoundException("Not found card with id = " + id);
        }
    }

    @Override
    public void deleteCard(Integer id) {
        try {
            Card card = cardRepository.findById(id).get();
            cardRepository.delete(card);
        } catch (Exception e) {
            throw new NotFoundException("Not found card with id = " + id);
        }
    }
}
