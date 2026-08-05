package com.jexis.jexis_backend.card.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

@Service
public class GetWalletCardsUseCase {
    private final CardRepository cardRepo;

    public GetWalletCardsUseCase(CardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public List<Card> execute(UUID walletId) {
        return cardRepo.findAllByTreasuryAccountIdAndIsDeletedFalse(walletId);
    }
}
