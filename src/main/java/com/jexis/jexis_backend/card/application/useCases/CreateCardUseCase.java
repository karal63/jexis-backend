package com.jexis.jexis_backend.card.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.user.domain.entities.User;

@Service
public class CreateCardUseCase {
    private final CardRepository repo;

    public CreateCardUseCase(CardRepository repo) {
        this.repo = repo;
    }

    public Card execute(User user, String last4, String status, java.math.BigDecimal limit, String brand, String type,
            String currency, Integer expYear) {
        Card card = new Card(user, last4, status, limit, brand, type, currency, expYear);
        return repo.save(card);
    }
}
