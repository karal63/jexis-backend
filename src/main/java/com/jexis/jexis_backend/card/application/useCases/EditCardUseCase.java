package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.application.dto.EditCardDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.exceptions.CardNotFoundException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;

@Service
public class EditCardUseCase {
    private final CardRepository repo;

    public EditCardUseCase(CardRepository repo) {
        this.repo = repo;
    }

    public Card execute(UUID id, EditCardDto dto) {
        Optional<Card> card = repo.findById(id);
        if (card.isEmpty()) {
            throw new CardNotFoundException();
        }

        card.ifPresent(foundCard -> {
            if (dto.getLast4() != null) {
                foundCard.setLast4(dto.getLast4());
            }
            if (dto.getStatus() != null) {
                foundCard.setStatus(dto.getStatus());
            }
            if (dto.getLimit() != null) {
                foundCard.setLimit(dto.getLimit());
            }
            repo.save(foundCard);
        });

        return card.get();
    }
}
