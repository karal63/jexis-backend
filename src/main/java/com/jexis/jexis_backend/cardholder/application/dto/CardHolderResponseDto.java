package com.jexis.jexis_backend.cardholder.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;

public record CardHolderResponseDto(
        UUID id,
        AccountResponseDto account,
        UserResponseDto user,
        String name,
        String addressLine1,
        String city,
        String state,
        String country,
        String postalCOde,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
