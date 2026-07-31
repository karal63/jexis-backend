package com.jexis.jexis_backend.user.application.useCases;

import com.jexis.jexis_backend.common.hashUtils.HashUtils;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateUserUseCase {
    private final UserRepository userRepository;
    private final GetUserByActivationTokenHashUseCase getUserByActivationTokenHashUseCase;

    public void execute(String token) {
        String hashedToken = HashUtils.sha256(token);

        User user = getUserByActivationTokenHashUseCase.execute(hashedToken);

        user.setIsActivated(true);
        userRepository.save(user);
    }
}
