package com.jexis.jexis_backend.auth.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConfirmPasswordResetDto(
        @NotNull String token,
        @NotNull String password
        ) {
}
