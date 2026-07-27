package com.jexis.jexis_backend.dispute.application.dto.notAsDescribed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceNotAsDescribedDto(
        String additionalDocumentation,
        @NotNull String canceled_at,
        @NotNull String cancellationReason,
        @NotNull String explanation,
        @NotNull String receivedAt
) {
}
