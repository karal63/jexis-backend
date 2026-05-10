package com.jexis.jexis_backend.auth.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
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

    public SignupUseCase(JwtUtil jwtUtil, CreateUserUseCase createUserUseCase) {
        this.jwtUtil = jwtUtil;
        this.createUserUseCase = createUserUseCase;
    }

    public SignupResult execute(CreateDto body) {
        User user = createUserUseCase.execute(body);
        TokenPair tokens = jwtUtil.generateTokens(
                user.getId(), user.getName(), user.getEmail(), user.getIsActivated());
        AuthUser authUser = new AuthUser(user.getId(), user.getName(), user.getEmail(), user.getIsActivated());

        return new SignupResult(authUser, tokens);
    }
}
