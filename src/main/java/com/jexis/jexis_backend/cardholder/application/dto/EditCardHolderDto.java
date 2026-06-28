package com.jexis.jexis_backend.cardholder.application.dto;

import com.jexis.jexis_backend.cardholder.domain.enums.CardHolderStatus;

public record EditCardHolderDto(
        BillingAddressDto billingAddress,
        SpendingControlsDto spendingControlsDto,
        CardHolderStatus status) {

}
