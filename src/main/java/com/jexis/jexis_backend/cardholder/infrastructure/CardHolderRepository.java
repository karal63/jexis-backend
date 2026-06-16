package com.jexis.jexis_backend.cardholder.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;

public interface CardHolderRepository extends JpaRepository<CardHolder, UUID> {
    Optional<CardHolder> findByEmail(String email);

    Optional<CardHolder> findByEmailAndAccountId(String email, UUID accountId);
}
