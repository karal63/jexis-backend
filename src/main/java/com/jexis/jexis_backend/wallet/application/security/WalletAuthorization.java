package com.jexis.jexis_backend.wallet.application.security;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
public class WalletAuthorization {
    private final HasRoleUseCase hasRoleUseCase;

    public WalletAuthorization(HasRoleUseCase hasRoleUseCase) {
        this.hasRoleUseCase = hasRoleUseCase;
    }

    public boolean canView(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canCreate(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }

    public boolean canEdit(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }

    public boolean canDelete(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }
}
