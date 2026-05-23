package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

@Service
public class GetCardUseCase {
    private final CardRepository repo;

    public GetCardUseCase(CardRepository repo) {
        this.repo = repo;
    }

    public Card execute(UUID id) {
        Optional<Card> card = repo.findById(id);
        if (card.isEmpty()) {
            throw new CardNotFoundException();
        }
        return card.get();
    }
}
