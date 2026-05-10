package com.jexis.jexis_backend.auth.application.dto;

import java.util.UUID;

public record AuthUser(
        UUID id,
        String name,
        String email,
        Boolean isActivated) {
}