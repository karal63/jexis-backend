package com.jexis.jexis_backend.auth.application.dto;

public record TokenPair(String accessToken, String refreshToken) {
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}