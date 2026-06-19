package com.jexis.jexis_backend.common.dtoHelpers;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
import com.jexis.jexis_backend.user.domain.entities.User;

@Service
public class DtoHelper {
    public UserResponseDto toUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getIsActivated(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
