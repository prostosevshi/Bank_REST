package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "number", nullable = false, length = 19)
    private String number;

    @Column(name = "owner", nullable = false, length = 64)
    private String owner;

    @Column(name = "expiry", nullable = false)
    private LocalDate expiry;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (balance == null) balance = 0.0;
        if (status == null) status = CardStatus.ACTIVE;
    }

    public String getMaskedNumber() {
        if (id == null || number.length() < 4) return "****";
        return "**** **** **** " + number.substring(number.length() - 4);
    }
}
