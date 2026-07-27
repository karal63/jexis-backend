package com.jexis.jexis_backend.dispute.application.dto.notAsDescribed;

import com.jexis.jexis_backend.dispute.application.dto.ReturnStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MerchandiseNotAsDescribedDto(
        String additionalDocumentation,
        @NotNull String explanation,
        @NotNull String receivedAt,
        String returnDescription,
        @NotNull ReturnStatus returnStatus,
        @NotNull String returnedAt
        ) {
}
