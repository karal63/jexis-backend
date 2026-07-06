package com.jexis.jexis_backend.account.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jexis.jexis_backend.account.domain.entities.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAllByOwnerIdAndIsDeletedFalse(UUID ownerId);

    Optional<Account> findByIdAndIsDeletedFalse(UUID id);

    Optional<Account> findByConnectAccountIdAndIsDeletedFalse(String connectAccountId);
}