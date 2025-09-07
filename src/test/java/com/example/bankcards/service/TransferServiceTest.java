package com.example.bankcards.service;

import com.example.bankcards.dto.TransferResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.repository.CardRepo;
import com.example.bankcards.repository.TransferRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferServiceTest {

    @Mock
    private CardRepo cardRepo;

    @Mock
    private TransferRepo transferRepo;

    @InjectMocks
    private TransferService transferService;

    private Card fromCard;
    private Card toCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromCard = new Card();
        fromCard.setId(UUID.randomUUID());
        fromCard.setBalance(BigDecimal.valueOf(1000));
        fromCard.setStatus(CardStatus.ACTIVE);
        fromCard.setUser(new com.example.bankcards.entity.User());
        fromCard.getUser().setUsername("testuser");

        toCard = new Card();
        toCard.setId(UUID.randomUUID());
        toCard.setBalance(BigDecimal.valueOf(500));
        toCard.setStatus(CardStatus.ACTIVE);
        toCard.setUser(new com.example.bankcards.entity.User());
        toCard.getUser().setUsername("otheruser");
    }

    @Test
    void transfer_success() {
        when(cardRepo.findById(fromCard.getId())).thenReturn(Optional.of(fromCard));
        when(cardRepo.findById(toCard.getId())).thenReturn(Optional.of(toCard));
        when(transferRepo.save(any(Transfer.class))).thenAnswer(i -> i.getArgument(0));

        TransferResponse result = transferService.transfer(fromCard.getId(), toCard.getId(), BigDecimal.valueOf(200), "testuser");

        assertEquals(BigDecimal.valueOf(800), fromCard.getBalance());
        assertEquals(BigDecimal.valueOf(700), toCard.getBalance());
        assertEquals(BigDecimal.valueOf(200), result.getAmount());
    }

    @Test
    void transfer_insufficientFunds() {
        when(cardRepo.findById(fromCard.getId())).thenReturn(Optional.of(fromCard));
        when(cardRepo.findById(toCard.getId())).thenReturn(Optional.of(toCard));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                transferService.transfer(fromCard.getId(), toCard.getId(), BigDecimal.valueOf(2000), "testuser"));
        assertEquals("Insufficient funds", ex.getMessage());
    }
}
