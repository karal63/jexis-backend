package com.jexis.jexis_backend.cardholder.application.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;
import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
public class CardHolderAuthorization {
    private CardHolderRepository repo;
    private final HasRoleUseCase hasRoleUseCase;

    public CardHolderAuthorization(CardHolderRepository repo, HasRoleUseCase hasRoleUseCase) {
        this.repo = repo;
        this.hasRoleUseCase = hasRoleUseCase;
    }

    public boolean canEdit(UUID userId, UUID cardHolderId) {
        CardHolder cardHolder = repo.findById(cardHolderId)
                .orElseThrow(() -> new AccessDeniedException("Access denied"));

        UUID accountId = cardHolder.getAccount().getId();

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

}
