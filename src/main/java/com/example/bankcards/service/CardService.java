package com.example.bankcards.service;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepo;
import com.example.bankcards.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;
    private final UserRepo userRepo;

    public Card createCard(CardRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Card card = Card.builder()
                .number(request.getNumber())
                .user(user)
                .expiry(request.getExpiry())
                .balance(request.getBalance())
                .status(CardStatus.ACTIVE)
                .build();

        return cardRepo.save(card);
    }

    public Card getCard(UUID id) {
        return cardRepo.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Page<Card> getCardsByUser(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("expiry").descending());

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cardRepo.findAllByUser(user, pageable);
    }

    public Card updateCard(Card card) {
        return cardRepo.save(card);
    }

    public void deleteCard(UUID id) {
        cardRepo.deleteById(id);
    }

    public void blockCard(UUID id) {
        Card card = getCard(id);
        card.setStatus(CardStatus.BLOCKED);
        cardRepo.save(card);
    }

    public void activateCard(UUID id) {
        Card card = getCard(id);
        card.setStatus(CardStatus.ACTIVE);
        cardRepo.save(card);
    }
}
