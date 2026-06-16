package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.domain.exceptions.ForbiddenException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;

/**
 * DeleteCardUseCase
 *
 * This service class implements the use case for deleting an existing card.
 * It contains only the business logic related to card deletion, such as
 * validating the user's permission and interacting with the repository to
 * remove the card.
 *
 * Author: Leo
 */
@Service
public class DeleteCardUseCase {
    private final CardRepository repo;

    public DeleteCardUseCase(CardRepository repo) {
        this.repo = repo;
    }

    /*
     * Deletes an existing card
     *
     * Accepts a {@link DeleteDto} payload from controller, validates the user's
     * permission, and removes the card from the repository.
     *
     * @param user the owner of the card and the id of the card to be deleted
     */
    public void execute(AuthUser user, UUID cardId) {
        Optional<Card> card = repo.findById(cardId);
        if (card.isEmpty()) {
            throw new CardNotFoundException();
        }

        if (!card.get().getCardHolder().getAccount().getOwner().getId().equals(user.id())) {
            throw new ForbiddenException();
        }

        repo.delete(card.get());
    }
}
