package com.jexis.jexis_backend.auth.application.dto;

public record SignupResult(AuthUser user, TokenPair tokens) {
}
