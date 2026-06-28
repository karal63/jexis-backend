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
        CardHolder cardHolder = repo.findById(id).orElseThrow(
                () -> new CardHolderNotFoundException());

        // Billing address
        if (dto.billingAddress().line1() != null) {
            cardHolder.setBillingAddressLine1(dto.billingAddress().line1());
        }
        if (dto.billingAddress().line2() != null) {
            cardHolder.setBillingAddressLine2(dto.billingAddress().line2());
        }
        if (dto.billingAddress().city() != null) {
            cardHolder.setBillingCity(dto.billingAddress().city());
        }
        if (dto.billingAddress().state() != null) {
            cardHolder.setBillingState(dto.billingAddress().state());
        }
        if (dto.billingAddress().country() != null) {
            cardHolder.setBillingCountry(dto.billingAddress().country());
        }
        if (dto.billingAddress().postalCode() != null) {
            cardHolder.setBillingPostalCode(dto.billingAddress().postalCode());
        }

        // Spending controls
        if (dto.spendingControlsDto().spendingLimits() != null) {
            cardHolder.setSpendingLimits(dto.spendingControlsDto().spendingLimits());
        }

        if (dto.status() != null) {
            cardHolder.setStatus(dto.status());
        }

        repo.save(cardHolder);

        return cardHolder;
    }
}
