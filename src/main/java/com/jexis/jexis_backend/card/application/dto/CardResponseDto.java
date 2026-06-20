package com.jexis.jexis_backend.card.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

public record CardResponseDto(
        UUID id,
        User cardHolder,
        Wallet treasuryAccount,
        User user,
        String last4,
        String status,
        String limit,
        String brand,
        String type,
        String currency,
        Long enpYear,
        LocalDateTime createdAt) {
}
