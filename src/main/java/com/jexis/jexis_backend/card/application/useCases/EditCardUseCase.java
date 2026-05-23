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
            if (dto.getBrand() != null) {
                foundCard.setBrand(dto.getBrand());
            }
            if (dto.getType() != null) {
                foundCard.setType(dto.getType());
            }
            if (dto.getCurrency() != null) {
                foundCard.setCurrency(dto.getCurrency());
            }
            if (dto.getExpYear() != null) {
                foundCard.setExpYear(dto.getExpYear());
            }
            repo.save(foundCard);
        });

        return card.get();
    }
}
