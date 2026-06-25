package com.jexis.jexis_backend.auth.application.useCases;

import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.auth.application.dto.LoginDto;
import com.jexis.jexis_backend.auth.application.dto.LoginResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.UserNotFoundException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

/**
 * LoginUseCase
 *
 * This service class implements the use case for logging in a user.
 * It contains only the business logic related to user authentication, such as
 * validating input data and interacting with the repository to verify user
 * credentials.
 *
 * Author: Leo
 */
@Service
public class LoginUseCase {
    JwtUtil jwtUtil;
    UserRepository userRepo;
    private final AsyncLogger logger;
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    public LoginUseCase(
            JwtUtil jwtUtil, UserRepository userRepo, AsyncLogger logger) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.logger = logger;
    }

    /*
     * Logs user in
     *
     * Accepts a {@link LoginDto} payload from controller, looks for
     * an account, validates credentials, generates tokens, and returns
     * {@link Account} with tokens.
     *
     * @param body the login data transfer object containing email and password
     * 
     * @return a {@link LoginResult} containing the authenticated user and token
     * pair
     */
    public LoginResult execute(LoginDto body) {
        logger.info("AUTH", "Login attempt for email: " + body.email());

        Optional<User> user = userRepo.findByEmail(body.email());

        if (!user.isPresent()) {
            logger.info("AUTH", "Login failed: user not found for email " + body.email());
            throw new UserNotFoundException();
        }

        if (!argon.matches(body.password(), user.get().getPassword())) {
            logger.info("AUTH", "Login failed: invalid password for email " + body.email());
            throw new UserNotFoundException();
        }

        TokenPair tokens = jwtUtil.generateTokens(user.get().getId(), user.get().getFirstName(), user.get().getEmail(),
                user.get().getIsActivated(), user.get().getRoles());

        UserResponseDto userResponse = new UserResponseDto(user.get().getId(), user.get().getFirstName(),
                user.get().getLastName(), user.get().getEmail(),
                user.get().getPhoneNumber(), user.get().getRoles(), user.get().getIsActivated(),
                user.get().getCreatedAt(),
                user.get().getUpdatedAt());

        logger.info("AUTH", "Login succeeded for user: " + user.get().getEmail());
        return new LoginResult(userResponse, tokens);
    }
}
