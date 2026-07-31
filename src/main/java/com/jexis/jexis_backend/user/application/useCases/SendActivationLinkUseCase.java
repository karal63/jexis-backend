package com.jexis.jexis_backend.user.application.useCases;

import com.jexis.jexis_backend.common.emailService.EmailService;
import com.jexis.jexis_backend.user.application.dto.ActivateUserDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendActivationLinkUseCase {
    private final EmailService emailService;
    private final GetUserUseCase getUserUseCase;

    public void execute(User user, String rawToken) {
        emailService.sendMail(
                user.getEmail(),
                "Activate your account",
                """
                        Hi, %s
                        
                        Please click the following link to activate your account:
                        
                        http://localhost:3000/api/user/activate?token=%s
                        """.formatted(user.getFirstName(), rawToken)
        );

    }
}
