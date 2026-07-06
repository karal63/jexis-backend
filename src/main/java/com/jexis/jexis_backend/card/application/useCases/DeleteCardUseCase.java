package com.jexis.jexis_backend.card.application.useCases;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jexis.jexis_backend.card.domain.enums.CardStatus;
import com.jexis.jexis_backend.stripe.application.useCases.EditCardStatusUseCase;
import com.stripe.param.issuing.CardUpdateParams;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

/**
 * DeleteCardUseCase
 * <p>
 * This service class implements the use case for deleting an existing card.
 * It contains only the business logic related to card deletion, such as
 * validating the user's permission and interacting with the repository to
 * remove the card.
 * <p>
 * Author: Leo
 */
@Service
public class DeleteCardUseCase {
    private final CardRepository repo;
    private final EditCardStatusUseCase editCardStatusUseCase;

    public DeleteCardUseCase(CardRepository repo, EditCardStatusUseCase editCardStatusUseCase) {
        this.repo = repo;
        this.editCardStatusUseCase = editCardStatusUseCase;
    }

    /**
     * Deletes an existing card
     * <p>
     * Accepts a {@param cardId} and removes the card from the repository.
     *
     * @param cardId id of the card we want to delete
     */
    public void execute(UUID cardId) {
        Card card = repo.findById(cardId).orElseThrow(CardNotFoundException::new);
        editCardStatusUseCase.execute(
                card.getCardHolder().getAccount().getConnectAccountId(),
                card.getStripeCardId(),
                CardStatus.canceled
        );

        card.setIsDeleted(true);
        card.setDeletedAt(LocalDateTime.now());

        repo.save(card);
    }
}
