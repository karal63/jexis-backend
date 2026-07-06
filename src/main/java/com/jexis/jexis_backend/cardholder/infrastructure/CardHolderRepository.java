package com.jexis.jexis_backend.cardholder.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;

public interface CardHolderRepository extends JpaRepository<CardHolder, UUID> {
    Optional<CardHolder> findByUserEmailAndAccountId(String email, UUID accountId);

    List<CardHolder> findAllByAccountIdAndIsDeletedFalse(UUID accountId);

    Optional<CardHolder> findByIdAndIsDeletedFalse(UUID id);
}
