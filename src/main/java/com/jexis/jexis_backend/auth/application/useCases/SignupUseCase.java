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
