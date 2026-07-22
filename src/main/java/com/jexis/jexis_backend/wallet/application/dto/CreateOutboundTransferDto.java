package com.jexis.jexis_backend.wallet.application.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOutboundTransferDto(
        @NotNull(message = "Amount is required")
        Long amount,

        @NotNull(message = "Currency is required")
        String currency,

        @NotNull(message = "Description is temporarily required")
        String description,

        @NotNull(message = "External account id is required")
        String externalAccountId // TODO later change to UUID
) {
}
