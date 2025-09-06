package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepo;
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

    public Card createCard(Card card) {
        return cardRepo.save(card);
    }

    public Card getCard(UUID id) {
        return cardRepo.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Page<Card> getCardsByOwner(String owner, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("expiry").descending());
        return cardRepo.findAllByOwner(owner, pageable);
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
