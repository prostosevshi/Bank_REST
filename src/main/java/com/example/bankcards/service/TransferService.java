package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.repository.CardRepo;
import com.example.bankcards.repository.TransferRepo;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CardRepo cardRepo;
    private final TransferRepo transferRepo;

    @Transactional
    public Transfer transfer(UUID fromCardId, UUID toCardId, BigDecimal amount, String username) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("amount must be greater than zero");

        Card fromCard = cardRepo.findById(fromCardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        Card toCard = cardRepo.findById(toCardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!fromCard.getStatus().equals(com.example.bankcards.entity.CardStatus.ACTIVE)
                || !toCard.getStatus().equals(com.example.bankcards.entity.CardStatus.ACTIVE)) {
            throw new RuntimeException("Both cards must be active");
        }

        if (!fromCard.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only transfer from your own card");
        }

        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));

        cardRepo.save(fromCard);
        cardRepo.save(toCard);

        Transfer transfer = Transfer.builder()
                .fromCard(fromCard)
                .toCard(toCard)
                .amount(amount)
                .build();

        return transferRepo.save(transfer);
    }
}
