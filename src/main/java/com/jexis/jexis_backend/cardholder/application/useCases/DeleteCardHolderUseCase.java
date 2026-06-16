package com.jexis.jexis_backend.cardholder.application.useCases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderNotFoundException;
import com.jexis.jexis_backend.cardholder.domain.exceptions.ForbiddenException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class DeleteCardHolderUseCase {
    private final CardHolderRepository repo;

    public DeleteCardHolderUseCase(CardHolderRepository repo) {
        this.repo = repo;
    }

    public void execute(AuthUser user, UUID cardHolderId) {
        Optional<CardHolder> cardHolder = repo.findById(cardHolderId);
        if (cardHolder.isEmpty()) {
            throw new CardHolderNotFoundException();
        }

        if (!cardHolder.get().getAccount().getOwner().getId().equals(user.id())) {
            throw new ForbiddenException();
        }

        cardHolder.get().setIsDeleted(true);
        cardHolder.get().setDeletedAt(LocalDateTime.now());
        repo.save(cardHolder.get());
    }
}
