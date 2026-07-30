package com.jexis.jexis_backend.passwordResetToken.application.useCases;

import com.jexis.jexis_backend.auth.application.dto.RequestPasswordResetDto;
import com.jexis.jexis_backend.passwordResetToken.domain.entities.PasswordResetToken;
import com.jexis.jexis_backend.passwordResetToken.infrastructure.PasswordResetTokenRepository;
import com.jexis.jexis_backend.user.application.useCases.GetUserByEmailUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatePasswordResetTokenUseCase {
    private final SecureRandom RANDOM = new SecureRandom();
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    private final PasswordResetTokenRepository repo;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public void execute(RequestPasswordResetDto dto) {
        Optional<User> user = getUserByEmailUseCase.execute(dto.email());

        if (user.isPresent()) {
            byte[] bytes = new byte[32]; // 256 bits
            RANDOM.nextBytes(bytes);
            String token = HexFormat.of().formatHex(bytes);

            String hashedToken = argon.encode(token);

            // store token
            PasswordResetToken passwordResetToken = new PasswordResetToken(
                    user.get(),
                    hashedToken,
                    LocalDateTime.now().plusHours(1), // token expires in 1 hour
                    LocalDateTime.now()
            );

            // save token
            repo.save(passwordResetToken);

            // send email with token to user
        }

    }
}
