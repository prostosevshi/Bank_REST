package com.example.bankcards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TransferResponse {
    private UUID id;
    private UUID fromCardId;
    private UUID toCardId;
    private BigDecimal amount;
}
