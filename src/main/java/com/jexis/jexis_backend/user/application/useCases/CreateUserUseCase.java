package com.jexis.jexis_backend.user.application.useCases;

import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.EmailExistsException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class CreateUserUseCase {
    private final UserRepository repo;
    private final AsyncLogger logger;
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    public CreateUserUseCase(UserRepository repo, AsyncLogger logger) {
        this.repo = repo;
        this.logger = logger;
    }

    public User execute(CreateDto body) {
        logger.info("USER", "Creating user for email: " + body.getEmail());

        Optional<User> existingUser = repo.findByEmail(body.getEmail());

        if (existingUser.isPresent()) {
            logger.info("USER", "User creation failed: email already exists " + body.getEmail());
            throw new EmailExistsException();
        }

        body.setPassword(argon.encode(body.getPassword()));

        User savedUser = repo.save(new User(body.getName(), body.getEmail(), body.getPassword()));
        logger.info("USER", "User created successfully: " + savedUser.getEmail());
        return savedUser;
    }
}