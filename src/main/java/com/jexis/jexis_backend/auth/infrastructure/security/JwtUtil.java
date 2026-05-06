package com.jexis.jexis_backend.auth.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.auth.application.dto.TokenPair;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final Key ACCESS_TOKEN = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Key REFRESH_TOKEN = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long atExpirationMs = 86400000; // 1 day
    private final long rtExpirationMs = 86400000 * 30;

    public TokenPair generateTokens(UUID id, String name, String email) {
        String accessToken = Jwts.builder()
                .setSubject(id.toString())
                .claim("name", name)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + atExpirationMs))
                .signWith(ACCESS_TOKEN)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(id.toString())
                .claim("name", name)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + rtExpirationMs))
                .signWith(REFRESH_TOKEN)
                .compact();

        return new TokenPair(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}