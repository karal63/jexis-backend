package com.jexis.jexis_backend.wallet.application.security;

import java.util.UUID;

import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
public class WalletAuthorization {
    private final HasRoleUseCase hasRoleUseCase;
    private final GetWalletUseCase getWalletUseCase;

    public WalletAuthorization(HasRoleUseCase hasRoleUseCase, GetWalletUseCase getWalletUseCase) {
        this.hasRoleUseCase = hasRoleUseCase;
        this.getWalletUseCase = getWalletUseCase;
    }

    public boolean canView(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canView(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return (hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN))
                && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canCreate(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }

    public boolean canEdit(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER) && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canDelete(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER) && wallet.getAccount().getId().equals(accountId);
    }
}
