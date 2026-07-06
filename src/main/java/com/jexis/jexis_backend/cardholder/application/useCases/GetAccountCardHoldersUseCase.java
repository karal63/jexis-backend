package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class GetAccountCardHoldersUseCase {
    private final CardHolderRepository cardRepo;

    public GetAccountCardHoldersUseCase(CardHolderRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public List<CardHolder> execute(UUID accountId) {
        return cardRepo.findAllByAccountIdAndIsDeletedFalse(accountId);
    }
}
