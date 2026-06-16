package com.jexis.jexis_backend.auth.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.auth.application.dto.LoginResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.domain.exception.InvalidRtException;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.UserNotFoundException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

import io.jsonwebtoken.Claims;

/**
 * RefreshTokensUseCase
 *
 * This service class implements the use case for refreshing authentication
 * tokens.
 * It contains only the business logic related to token refresh, such as
 * validating the refresh token and generating new access and refresh tokens.
 *
 * Author: Leo
 */
@Service
public class RefreshTokensUseCase {
    JwtUtil jwtUtil;
    UserRepository repo;

    public RefreshTokensUseCase(JwtUtil jwtUtil, UserRepository repo) {
        this.jwtUtil = jwtUtil;
        this.repo = repo;
    }

    /*
     * Refreshes authentication tokens
     *
     * Accepts a refresh token from the client, validates it, and generates
     * new access and refresh tokens.
     *
     * @param token the refresh token
     * 
     * @return a {@link LoginResult} containing the authenticated user and token
     * pair
     */
    public LoginResult execute(String token) {
        if (!jwtUtil.validateRefreshToken(token)) {
            throw new InvalidRtException();
        }

        Claims claims = jwtUtil.extractClaimsRt(token);

        Optional<User> dbUser = repo.findById(UUID.fromString(claims.get("id", String.class)));

        if (dbUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        TokenPair tokens = jwtUtil.generateTokens(dbUser.get().getId(), dbUser.get().getFirstName(),
                dbUser.get().getEmail(),
                dbUser.get().getIsActivated());

        AuthUser user = new AuthUser(dbUser.get().getId(), dbUser.get().getFirstName(), dbUser.get().getEmail(),
                dbUser.get().getIsActivated());

        return new LoginResult(user, tokens);
    }
}
