package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.application.dto.EditCardDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.cardholder.application.useCases.GetCardHolderUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.EditCardStatusUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.SetCardLimitsUseCase;

/**
 * EditCardUseCase
 *
 * This service class implements the use case for editing an existing card.
 * It contains only the business logic related to card editing, such as
 * validating input data and interacting with the repository to update the card.
 *
 * Author: Leo
 */
@Service
public class EditCardUseCase {
    private final CardRepository repo;
    private final GetCardHolderUseCase getCardHolderUseCase;
    private final SetCardLimitsUseCase setCardLimitsUseCase;
    private final EditCardStatusUseCase editCardStatusUseCase;

    public EditCardUseCase(CardRepository repo, GetCardHolderUseCase getCardHolderUseCase,
            SetCardLimitsUseCase setCardLimitsUseCase, EditCardStatusUseCase editCardStatusUseCase) {
        this.repo = repo;
        this.getCardHolderUseCase = getCardHolderUseCase;
        this.setCardLimitsUseCase = setCardLimitsUseCase;
        this.editCardStatusUseCase = editCardStatusUseCase;
    }

    /**
     * Edits an existing card
     *
     * Accepts a {@link EditCardDto} payload from controller, updates the card,
     * and returns the updated card.
     *
     * @param id the id of the card to be edited and the new card details such as
     *           last4, status, limit, brand, type, currency, and expYear
     * 
     * @return the updated card entity
     */
    public Card execute(UUID id, EditCardDto dto) {
        Card card = repo.findById(id).orElseThrow(() -> new CardNotFoundException());

        if (dto.status() != null) {
            editCardStatusUseCase.execute(card.getCardHolder().getAccount().getConnectAccountId(),
                    card.getStripeCardId(), dto.status());
            card.setStatus(dto.status());
        }

        if (dto.spendingLimits() != null) {
            setCardLimitsUseCase.execute(card.getCardHolder().getAccount().getConnectAccountId(),
                    card.getStripeCardId(), dto.spendingLimits());
            card.setSpendingLimits(dto.spendingLimits());
        }

        Card saved = repo.save(card);

        return saved;
    }
}
