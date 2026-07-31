package com.jexis.jexis_backend.auth.application.useCases;

import com.jexis.jexis_backend.auth.application.dto.ConfirmPasswordResetDto;
import com.jexis.jexis_backend.auth.domain.exception.InvalidPasswordResetTokenException;
import com.jexis.jexis_backend.passwordResetToken.application.useCases.GetPasswordResetTokenUseCase;
import com.jexis.jexis_backend.passwordResetToken.application.useCases.UsePasswordResetTokenUseCase;
import com.jexis.jexis_backend.passwordResetToken.domain.entities.PasswordResetToken;
import com.jexis.jexis_backend.user.application.useCases.ChangePasswordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmPasswordResetUseCase {
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    private final GetPasswordResetTokenUseCase getPasswordResetTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final UsePasswordResetTokenUseCase usePasswordResetTokenUseCase;

    public void execute(ConfirmPasswordResetDto body) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenUseCase.execute(body.tokenId());

        if (!argon.matches(body.token(), passwordResetToken.getTokenHash())) {
            throw new InvalidPasswordResetTokenException();
        }

        changePasswordUseCase.execute(passwordResetToken.getUser().getId(), body.password());
        usePasswordResetTokenUseCase.execute(passwordResetToken.getId());
    }
}
