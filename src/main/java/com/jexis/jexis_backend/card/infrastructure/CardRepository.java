package com.jexis.jexis_backend.card.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.card.domain.entities.Card;

public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByCardHolderAccountIdAndIsDeletedFalse(UUID accountId);

    Optional<Card> findByIdAndIsDeletedFalse(UUID id);

}
