package com.jexis.jexis_backend.dispute.application.dto.fraudulent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FraudulentEvidenceDto(
        String additional_documentation,
        @NotNull String explanation
) {

}
