package com.jexis.jexis_backend.auth.application.useCases;

import com.jexis.jexis_backend.auth.application.dto.ConfirmPasswordResetDto;
import com.jexis.jexis_backend.auth.domain.exception.InvalidPasswordResetTokenException;
import com.jexis.jexis_backend.passwordResetToken.application.useCases.GetPasswordResetTokenByTokenUseCase;
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
    private final ChangePasswordUseCase changePasswordUseCase;
    private final UsePasswordResetTokenUseCase usePasswordResetTokenUseCase;
    private final GetPasswordResetTokenByTokenUseCase getPasswordResetTokenByTokenUseCase;

    public void execute(ConfirmPasswordResetDto body) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenByTokenUseCase.execute(body.token());

        changePasswordUseCase.execute(passwordResetToken.getUser().getId(), body.password());
        usePasswordResetTokenUseCase.execute(passwordResetToken.getId());
    }
}
