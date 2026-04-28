package com.jexis.jexis_backend.account.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jexis.jexis_backend.account.domain.entities.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByName(String name);
}