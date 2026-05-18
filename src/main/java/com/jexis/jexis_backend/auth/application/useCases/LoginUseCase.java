package com.jexis.jexis_backend.auth.application.useCases;

import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.auth.application.dto.LoginDto;
import com.jexis.jexis_backend.auth.application.dto.LoginResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.UserNotFoundException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class LoginUseCase {
    JwtUtil jwtUtil;
    UserRepository userRepo;
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    public LoginUseCase(
            JwtUtil jwtUtil, UserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    public LoginResult execute(LoginDto body) {
        Optional<User> user = userRepo.findByEmail(body.email());

        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        if (!argon.matches(body.password(), user.get().getPassword())) {
            throw new UserNotFoundException();
        }

        TokenPair tokens = jwtUtil.generateTokens(user.get().getId(), user.get().getName(), user.get().getEmail(),
                user.get().getIsActivated());
        AuthUser authUser = new AuthUser(user.get().getId(), user.get().getName(), user.get().getEmail(),
                user.get().getIsActivated());

        return new LoginResult(authUser, tokens);
    }
}
