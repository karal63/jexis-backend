package com.jexis.jexis_backend.auth.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.auth.application.dto.LoginDto;
import com.jexis.jexis_backend.auth.application.dto.LoginResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class LoginUseCase {
    JwtUtil jwtUtil;
    UserRepository userRepo;

    public LoginUseCase(JwtUtil jwtUtil, UserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    public LoginResult execute(LoginDto body) {
        User user = userRepo.findByEmail(body.email());

        if (!user.getPassword().equals(body.password())) {
            throw new Error();
        }

        TokenPair tokens = jwtUtil.generateTokens(user.getId(), user.getName(), user.getEmail(), user.getIsActivated());
        AuthUser authUser = new AuthUser(user.getId(), user.getName(), user.getEmail(), user.getIsActivated());

        return new LoginResult(authUser, tokens);
    }
}
