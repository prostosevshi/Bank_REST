package com.example.bankcards.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CardRequest {
    private String number;
    private UUID userId;
    private LocalDate expiry;
    private BigDecimal balance;
}
