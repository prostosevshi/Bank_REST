package com.example.bankcards.service;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepo;
import com.example.bankcards.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepo cardRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CardService cardService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
    }

    @Test
    void createCard_success() {
        CardRequest request = new CardRequest();
        request.setNumber("1234567812345678");
        request.setUserId(user.getId());
        request.setExpiry(LocalDate.now().plusYears(1));
        request.setBalance(BigDecimal.valueOf(1000));

        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(cardRepo.save(any(Card.class))).thenAnswer(i -> i.getArgument(0));

        Card created = cardService.createCard(request);

        assertNotNull(created);
        assertEquals(CardStatus.ACTIVE, created.getStatus());
        assertEquals(user, created.getUser());
        assertEquals("1234567812345678", created.getNumber());
    }

    @Test
    void createCard_userNotFound() {
        CardRequest request = new CardRequest();
        request.setUserId(UUID.randomUUID());

        when(userRepo.findById(any())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cardService.createCard(request));
        assertEquals("User not found", ex.getMessage());
    }
}
