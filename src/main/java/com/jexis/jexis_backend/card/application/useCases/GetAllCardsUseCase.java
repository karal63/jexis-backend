package com.jexis.jexis_backend.card.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

@Service
public class GetAllCardsUseCase {
    private final CardRepository repo;

    public GetAllCardsUseCase(CardRepository repo) {
        this.repo = repo;
    }

    public List<Card> execute() {
        return repo.findAll();
    }
}
