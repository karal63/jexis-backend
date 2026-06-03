package com.jexis.jexis_backend.auth.application.dto;

import java.util.UUID;

/**
 * AuthUser
 *
 * Data Transfer Object used for authentication requests. It encapsulates the
 * necessary data required for user authentication, such as the user ID, name,
 * email, and activation status.
 *
 * Author: Leo
 */
public record AuthUser(
        UUID id,
        String name,
        String email,
        Boolean isActivated) {
}