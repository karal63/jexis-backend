package com.jexis.jexis_backend.cardholder.application.useCases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderNotFoundException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class DeleteCardHolderUseCase {
    private final CardHolderRepository repo;
    private final GetCardHolderUseCase getCardHolderUseCase;

    public DeleteCardHolderUseCase(CardHolderRepository repo, GetCardHolderUseCase getCardHolderUseCase) {
        this.repo = repo;
        this.getCardHolderUseCase = getCardHolderUseCase;
    }

    public void execute(UUID cardHolderId) {
        CardHolder cardHolder = getCardHolderUseCase.execute(cardHolderId);

        cardHolder.setIsDeleted(true);
        cardHolder.setDeletedAt(LocalDateTime.now());

        repo.save(cardHolder);
    }
}
