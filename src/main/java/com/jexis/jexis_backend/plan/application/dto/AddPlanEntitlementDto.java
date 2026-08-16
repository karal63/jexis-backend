package com.jexis.jexis_backend.plan.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddPlanEntitlementDto(
        @NotNull UUID entitlementId,
        @NotBlank String value
) {
}
