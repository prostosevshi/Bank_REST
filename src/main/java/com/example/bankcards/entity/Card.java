package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry", nullable = false)
    private LocalDate expiry;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "last4", nullable = false)
    private String last4;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (balance == null) balance = BigDecimal.valueOf(0.0);
        if (status == null) status = CardStatus.ACTIVE;
        if (last4 == null && number != null && number.length() >= 4) {
            last4 = number.substring(number.length() - 4);
        }
    }

    public String getMaskedNumber() {
        if (id == null || number.length() < 4) return "****";
        return "**** **** **** " + number.substring(number.length() - 4);
    }
}
