package com.jexis.jexis_backend.auth.application.dto;

/**
 * LoginDto
 *
 * Data Transfer Object used for login requests. It encapsulates the
 * necessary data required for user login, such as the user's email and
 * password.
 *
 * Author: Leo
 */
public record LoginDto(String email, String password) {

}
