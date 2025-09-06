package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepo extends JpaRepository<Card, UUID> {

    Page<Card> findAllByOwner(String owner, Pageable pageable);

    Page<Card> findAllByStatus(String status, Pageable pageable);
}
