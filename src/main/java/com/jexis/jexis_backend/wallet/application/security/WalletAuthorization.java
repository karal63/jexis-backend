package com.jexis.jexis_backend.wallet.application.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Component
public class WalletAuthorization {
    private final WalletRepository repo;
    private final HasRoleUseCase hasRoleUseCase;

    public WalletAuthorization(WalletRepository repo, HasRoleUseCase hasRoleUseCase) {
        this.repo = repo;
        this.hasRoleUseCase = hasRoleUseCase;
    }

    public boolean canEdit(UUID userId, UUID walletId) {
        Wallet wallet = repo.findById(walletId).orElseThrow(() -> new AccessDeniedException("Access denied"));
        return hasRoleUseCase.execute(userId, wallet.getAccount().getId(), Role.OWNER);
    }
}
