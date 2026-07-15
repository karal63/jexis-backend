package com.jexis.jexis_backend.authorization.infrastructure;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorizationRepository extends JpaRepository<Authorization, UUID> {
    List<Authorization> findByWalletId(UUID walletId);
}
