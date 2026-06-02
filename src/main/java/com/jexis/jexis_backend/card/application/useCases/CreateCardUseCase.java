package com.jexis.jexis_backend.card.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.user.domain.entities.User;

@Service
public class CreateCardUseCase {
    private final CardRepository repo;
    private final AsyncLogger logger;

    public CreateCardUseCase(CardRepository repo, AsyncLogger logger) {
        this.repo = repo;
        this.logger = logger;
    }

    public Card execute(User user, String last4, String status, java.math.BigDecimal limit, String brand, String type,
            String currency, Integer expYear) {
        logger.info("CARD", "Creating card for user: " + user.getId() + " | brand: " + brand);

        Card card = new Card(user, last4, status, limit, brand, type, currency, expYear);
        Card saved = repo.save(card);
        logger.info("CARD", "Card created successfully with id: " + saved.getId());
        return saved;
    }
}
