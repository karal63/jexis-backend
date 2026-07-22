package com.jexis.jexis_backend.externalAccount.infrastructure;

import com.jexis.jexis_backend.externalAccount.domain.entities.ExternalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExternalAccountRepository extends JpaRepository<ExternalAccount, UUID> {
    Optional<ExternalAccount> findByStripeExternalAccountIdAndIsDeletedFalse(String stripeExternalAccountId);
}
