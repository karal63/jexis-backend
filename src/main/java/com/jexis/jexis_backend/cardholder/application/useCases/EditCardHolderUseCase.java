package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.cardholder.application.dto.EditCardHolderDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderNotFoundException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class EditCardHolderUseCase {
    private final CardHolderRepository repo;

    public EditCardHolderUseCase(CardHolderRepository repo) {
        this.repo = repo;
    }

    public CardHolder execute(UUID id, EditCardHolderDto dto) {
        Optional<CardHolder> cardHolder = repo.findById(id);
        if (cardHolder.isEmpty()) {
            throw new CardHolderNotFoundException();
        }

        cardHolder.ifPresent(foundCardHolder -> {
            if (dto.getName() != null) {
                foundCardHolder.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                foundCardHolder.setEmail(dto.getEmail());
            }
            if (dto.getPhoneNumber() != null) {
                foundCardHolder.setPhoneNumber(dto.getPhoneNumber());
            }
            if (dto.getAddressLine1() != null) {
                foundCardHolder.setAddressLine1(dto.getAddressLine1());
            }
            if (dto.getCity() != null) {
                foundCardHolder.setCity(dto.getCity());
            }
            if (dto.getState() != null) {
                foundCardHolder.setState(dto.getState());
            }
            if (dto.getCountry() != null) {
                foundCardHolder.setCountry(dto.getCountry());
            }
            if (dto.getPostalCode() != null) {
                foundCardHolder.setPostalCode(dto.getPostalCode());
            }
            repo.save(foundCardHolder);
        });

        return cardHolder.get();
    }
}
