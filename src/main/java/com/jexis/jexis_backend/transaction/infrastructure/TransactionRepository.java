package com.jexis.jexis_backend.transaction.infrastructure;

import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByWalletId(UUID walletId);
    List<Transaction> findByCardId(UUID cardId);
    Optional<Transaction> findByStripeObjectId(String stripeObjectId);
}
