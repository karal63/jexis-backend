package com.jexis.jexis_backend.cardholder.application.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CreateCardHolderDto(
        @NotBlank UUID accountId,
        @NotBlank UUID userId,
        @NotBlank String addressLine1,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String postalCode) {
}
