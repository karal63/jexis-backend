package com.jexis.jexis_backend.auth.application.dto;

public record LoginResult(AuthUser user, TokenPair tokens) {
}
