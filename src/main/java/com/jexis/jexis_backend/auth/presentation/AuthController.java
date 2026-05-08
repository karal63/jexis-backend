package com.jexis.jexis_backend.auth.presentation;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.infrastructure.security.JwtUtil;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.application.useCases.CreateUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public CreateUserUseCase createUserUseCase;
    public JwtUtil jwtUtil;

    public AuthController(CreateUserUseCase createUserUseCase, JwtUtil jwtUtil) {
        this.createUserUseCase = createUserUseCase;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CreateDto body) {
        User user = createUserUseCase.execute(body);
        TokenPair tokens = jwtUtil.generateTokens(
                user.getId(), user.getName(), user.getEmail());

        ResponseCookie accessCookie = ResponseCookie.from("access_token", tokens.getAccessToken())
                .httpOnly(true)
                .secure(true) // set false only for local dev without HTTPS
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict") // or "Lax" / "None"
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokens.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}
