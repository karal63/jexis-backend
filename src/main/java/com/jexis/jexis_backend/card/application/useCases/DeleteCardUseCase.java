package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.domain.exceptions.ForbiddenException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;

@Service
public class DeleteCardUseCase {
    private final CardRepository repo;

    public DeleteCardUseCase(CardRepository repo) {
        this.repo = repo;
    }

    public void execute(AuthUser user, UUID cardId) {
        Optional<Card> card = repo.findById(cardId);
        if (card.isEmpty()) {
            throw new CardNotFoundException();
        }

        // temporal solution
        if (!card.get().getUser().getId().equals(user.id())) {
            throw new ForbiddenException();
        }

        repo.delete(card.get());
    }
}
