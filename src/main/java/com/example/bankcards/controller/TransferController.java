package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferResponse;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.repository.TransferRepo;
import com.example.bankcards.service.TransferService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public TransferResponse makeTransfer(@RequestBody TransferRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Transfer transfer = transferService.transfer(
                request.getFromCardId(),
                request.getToCardId(),
                request.getAmount(),
                username
        );

        return new TransferResponse(
                transfer.getId(),
                transfer.getFromCard().getId(),
                transfer.getToCard().getId(),
                transfer.getAmount()
        );
    }

    @Data
    public static class TransferRequest {
        private UUID fromCardId;
        private UUID toCardId;
        private BigDecimal amount;
    }
}
