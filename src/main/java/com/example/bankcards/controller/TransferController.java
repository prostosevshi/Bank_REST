package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.dto.TransferResponse;
import com.example.bankcards.entity.Transfer;
import com.example.bankcards.repository.TransferRepo;
import com.example.bankcards.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Transfers", description = "Endpoints for transferring money between user's cards")
public class TransferController {

    private final TransferService transferService;

    @Operation(
            summary = "Make a transfer between cards",
            description = "Allows a user to transfer money from one of their active cards to another card they own."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or insufficient funds"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping
    public TransferResponse makeTransfer(@RequestBody @Valid TransferRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return transferService.transfer(
                request.getFromCardId(),
                request.getToCardId(),
                request.getAmount(),
                username
        );
    }
}
