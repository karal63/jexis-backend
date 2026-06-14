package com.jexis.jexis_backend.card.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;

/**
 * CreateCardUseCase
 *
 * This service class implements the use case for creating a new card.
 * It contains only the business logic related to card creation, such as
 * validating input data and interacting with the repository to persist the new
 * card.
 *
 * Author: Leo
 */
@Service
public class CreateCardUseCase {
    private final CardRepository repo;
    private final AsyncLogger logger;

    public CreateCardUseCase(CardRepository repo, AsyncLogger logger) {
        this.repo = repo;
        this.logger = logger;
    }

    /*
     * Creates a new card
     *
     * Accepts a {@link CreateDto} payload from controller, creates a new card,
     * and returns the created card.
     *
     * @param cardHolder the owner card holder and card details such as last4,
     * status, limit, brand, type, currency, and expYear
     * 
     * @return the created card entity
     */
    public Card execute(CardHolder cardHolder, String last4, String status, java.math.BigDecimal limit, String brand,
            String type,
            String currency, Integer expYear) {
        logger.info("CARD", "Creating card for cardHolder: " + cardHolder.getId() + " | brand: " + brand);

        Card card = new Card(cardHolder, last4, status, limit, brand, type, currency, expYear);
        Card saved = repo.save(card);
        logger.info("CARD", "Card created successfully with id: " + saved.getId());
        return saved;
    }
}
