package com.example.bankcards.controller;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/my")
    public Page<CardResponse> getMyCards(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return cardService.getCardsByUser(username, page, size)
                .map(CardResponse::fromEntity);
    }

    @GetMapping("/{id}")
    public CardResponse getCard(@PathVariable UUID id) {
        return CardResponse.fromEntity(cardService.getCard(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<CardResponse> getCardsByStatus(@PathVariable("status") CardStatus status,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return cardService.getCardsByStatus(status, page, size)
                .map(CardResponse::fromEntity);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse createCard(@RequestBody @Valid CardRequest request) {
        return CardResponse.fromEntity(cardService.createCard(request));
    }

    @PostMapping("/{id}/block")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse blockCard(@PathVariable UUID id) {
        cardService.blockCard(id);
        return CardResponse.fromEntity(cardService.getCard(id));
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse activateCard(@PathVariable UUID id) {
        cardService.activateCard(id);
        return CardResponse.fromEntity(cardService.getCard(id));
    }

    @PostMapping("/{id}/request-block")
    @PreAuthorize("hasAuthority('USER')")
    public CardResponse requestBlockCard(@PathVariable UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Card card = cardService.getCard(id);
        if (!card.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only request block for your own cards");
        }
        cardService.blockCard(id);
        return CardResponse.fromEntity(cardService.getCard(id));
    }
}
