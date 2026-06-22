package com.jexis.jexis_backend.account.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

@Service
public class GetAccountCardsUseCase {
    private final CardRepository cardRepo;

    public GetAccountCardsUseCase(CardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public List<Card> execute(UUID accountId) {
        return cardRepo.findByCardHolderAccountId(accountId);
    }
}
