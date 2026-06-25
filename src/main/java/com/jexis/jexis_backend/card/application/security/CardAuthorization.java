package com.jexis.jexis_backend.card.application.security;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.card.application.useCases.GetCardUseCase;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
public class CardAuthorization {
    private final HasRoleUseCase hasRoleUseCase;
    private final GetCardUseCase getCardUseCase;

    public CardAuthorization(HasRoleUseCase hasRoleUseCase, GetCardUseCase getCardUseCase) {
        this.hasRoleUseCase = hasRoleUseCase;
        this.getCardUseCase = getCardUseCase;
    }

    public boolean canViewAll(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canView(UUID userId, UUID accountId, UUID cardId) {
        Card card = getCardUseCase.execute(cardId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN)
                || card.getUser().getId().equals(userId);
    }

    public boolean canCreate(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canEdit(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canDelete(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }
}
