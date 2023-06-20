package com.example.webapi.controller;

import com.example.webapi.entity.Card;
import com.example.webapi.dtos.request.UpsertCardRequest;
import com.example.webapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    //2. Lấy chi tiết card
    @GetMapping("/cards/{id}")
    public Card getBookById(@PathVariable Integer id) {
        return cardService.getCardById(id);
    }

    //3. Tạo Card
    @PostMapping("cards")
    public Card createBook(@RequestBody @Validated UpsertCardRequest request) {
        return cardService.createCard(request);
    }

    //4. Cập nhật Card
    @PutMapping("cards/{id}")
    public Card updateBook(@PathVariable Integer id, @RequestBody @Validated UpsertCardRequest request) {
        return cardService.updateCard(id,request);
    }

    //5. Xóa Card
    @DeleteMapping("books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        cardService.deleteCard(id);
    }
}
