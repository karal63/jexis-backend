package com.jexis.jexis_backend.auth.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.auth.application.dto.SignupResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.application.useCases.CreateUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

/**
 * SignupUseCase
 *
 * This service class implements the use case for signing up a new user.
 * It contains only the business logic related to user registration, such as
 * validating input data and interacting with the repository to persist the new
 * user.
 *
 * Author: Leo
 */
@Service
public class SignupUseCase {
    JwtUtil jwtUtil;
    CreateUserUseCase createUserUseCase;
    private final AsyncLogger logger;

    public SignupUseCase(JwtUtil jwtUtil, CreateUserUseCase createUserUseCase, AsyncLogger logger) {
        this.jwtUtil = jwtUtil;
        this.createUserUseCase = createUserUseCase;
        this.logger = logger;
    }

    /*
     * Signs up a new user
     *
     * Accepts a {@link CreateDto} payload from controller, creates a new user,
     * generates tokens, and returns {@link SignupResult} with the new user and
     * tokens.
     *
     * @param body the signup data transfer object containing email and password
     * 
     * @return a {@link SignupResult} containing the new user and token
     * pair
     */
    public SignupResult execute(CreateDto body) {
        logger.info("AUTH", "Signup started for email: " + body.getEmail());

        User user = createUserUseCase.execute(body);

        TokenPair tokens = jwtUtil.generateTokens(
                user.getId(), user.getName(), user.getEmail(), user.getIsActivated());

        AuthUser authUser = new AuthUser(user.getId(), user.getName(), user.getEmail(), user.getIsActivated());
        logger.info("AUTH", "Signup completed for user: " + user.getEmail());
        return new SignupResult(authUser, tokens);
    }
}
