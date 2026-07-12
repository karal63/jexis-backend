package com.jexis.jexis_backend.wallet.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findAllByAccountIdAndIsDeletedFalse(UUID accountId);

    Optional<Wallet> findByIdAndIsDeletedFalse(UUID accountId);

    Optional<Wallet> findByStripeFinancialAccountIdAndIsDeletedFalse(String stripeFinancialAccountId);
}
