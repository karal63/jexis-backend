package com.jexis.jexis_backend.wallet.application.dto;

import com.jexis.jexis_backend.wallet.domain.entities.ReceivedCreditsNetwork;

public record AddReceivedCreditsDto(
        Long amount,
        String currency,
        ReceivedCreditsNetwork network,
        String description
) {

}
