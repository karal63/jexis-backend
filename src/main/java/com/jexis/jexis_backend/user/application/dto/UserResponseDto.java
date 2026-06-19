package com.jexis.jexis_backend.user.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
                UUID id,
                String firstName,
                String lastName,
                String email,
                String phoneNumber,
                Boolean isActivated,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
