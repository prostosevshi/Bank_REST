package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CardResponse {

    private UUID id;
    private String maskedNumber;
    private String ownerUsername;
    private LocalDate expiry;
    private CardStatus status;
    private BigDecimal balance;

    public static CardResponse fromEntity(Card card) {
        return new CardResponse(
                card.getId(),
                card.getMaskedNumber(),
                card.getUser().getUsername(),
                card.getExpiry(),
                card.getStatus(),
                card.getBalance()
        );
    }
}
