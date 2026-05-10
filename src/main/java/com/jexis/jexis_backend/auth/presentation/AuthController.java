package com.jexis.jexis_backend.auth.presentation;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.LoginDto;
import com.jexis.jexis_backend.auth.application.dto.LoginResult;
import com.jexis.jexis_backend.auth.application.dto.SignupResult;
import com.jexis.jexis_backend.auth.application.dto.TokenPair;
import com.jexis.jexis_backend.auth.application.useCases.LoginUseCase;
import com.jexis.jexis_backend.auth.application.useCases.SignupUseCase;
import com.jexis.jexis_backend.user.application.dto.CreateDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public SignupUseCase signupUseCase;
    public LoginUseCase loginUseCase;

    public AuthController(SignupUseCase signupUseCase, LoginUseCase loginUseCase) {
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CreateDto body) {
        SignupResult result = signupUseCase.execute(body);
        TokenPair tokens = result.tokens();

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
                .body(result.user());
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/") // MUST match original path
                .maxAge(0) // expire immediately
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh") // MUST match original path
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto body) {
        LoginResult result = loginUseCase.execute(body);
        TokenPair tokens = result.tokens();

        ResponseCookie accessCookie = ResponseCookie.from("access_token", tokens.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
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
                .body(result.user());
    }
}
