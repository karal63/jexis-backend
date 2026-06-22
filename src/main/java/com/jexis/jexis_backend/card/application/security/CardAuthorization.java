package com.jexis.jexis_backend.card.application.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.member.application.useCases.CanAccessUseCase;
import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
public class CardAuthorization {
    private final CardRepository repo;
    private final HasRoleUseCase hasRoleUseCase;
    private final CanAccessUseCase canAccessUseCase;

    public CardAuthorization(CardRepository repo, HasRoleUseCase hasRoleUseCase, CanAccessUseCase canAccessUseCase) {
        this.repo = repo;
        this.hasRoleUseCase = hasRoleUseCase;
        this.canAccessUseCase = canAccessUseCase;
    }

    public boolean canView(UUID userId, UUID cardId) {
        Card card = repo.findById(cardId).orElseThrow(() -> new AccessDeniedException("Access denied"));
        return canAccessUseCase.execute(userId, card.getCardHolder().getAccount().getId());
    }

    public boolean canEdit(UUID userId, UUID cardId) {
        Card card = repo.findById(cardId).orElseThrow(() -> new AccessDeniedException("Access denied"));
        return hasRoleUseCase.execute(userId, card.getCardHolder().getAccount().getId(), Role.ADMIN)
                || hasRoleUseCase.execute(userId, card.getCardHolder().getAccount().getId(), Role.OWNER);
    }
}
