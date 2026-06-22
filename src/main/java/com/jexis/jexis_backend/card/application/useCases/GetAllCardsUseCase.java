package com.jexis.jexis_backend.card.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

/**
 * GetAllCardsUseCase
 *
 * This service class implements the use case for retrieving all cards.
 * It contains only the business logic related to fetching cards, such as
 * interacting with the repository to fetch all persisted cards.
 *
 * Author: Leo
 */
@Service
public class GetAllCardsUseCase {
    private final CardRepository repo;

    public GetAllCardsUseCase(CardRepository repo) {
        this.repo = repo;
    }

    /*
     * Retrieves all cards
     *
     * Returns a list of all card entities stored in the repository.
     *
     * @return a list of all card entities
     */
    public List<Card> execute() {
        return repo.findAll();
    }
}
