package com.jexis.jexis_backend.card.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jexis.jexis_backend.cardholder.application.dto.CardHolderResponseDto;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
import com.jexis.jexis_backend.wallet.application.dto.WalletResponseDto;

public record CardResponseDto(
                UUID id,
                CardHolderResponseDto cardHolder,
                WalletResponseDto treasuryAccount,
                UserResponseDto user,
                String last4,
                String status,
                BigDecimal limit,
                String brand,
                String type,
                String currency,
                Long expYear,
                LocalDateTime createdAt) {
}
