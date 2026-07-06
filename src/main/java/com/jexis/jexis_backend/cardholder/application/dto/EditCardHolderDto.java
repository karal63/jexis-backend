package com.jexis.jexis_backend.cardholder.application.dto;

import com.jexis.jexis_backend.cardholder.domain.enums.CardHolderStatus;

public record EditCardHolderDto(
        BillingAddressDto billingAddress,
        SpendingControlsDto spendingControls,
        CardHolderStatus status) {
    public static EditCardHolderDto withStatus(CardHolderStatus status) {
        return new EditCardHolderDto(null, null, status);
    }
}
