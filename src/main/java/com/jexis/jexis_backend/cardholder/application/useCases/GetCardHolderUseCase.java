package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderNotFoundException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class GetCardHolderUseCase {
    private final CardHolderRepository repo;

    public GetCardHolderUseCase(CardHolderRepository repo) {
        this.repo = repo;
    }

    public CardHolder execute(UUID id) {
        Optional<CardHolder> cardHolder = repo.findById(id);
        if (cardHolder.isEmpty()) {
            throw new CardHolderNotFoundException();
        }
        return cardHolder.get();
    }
}
