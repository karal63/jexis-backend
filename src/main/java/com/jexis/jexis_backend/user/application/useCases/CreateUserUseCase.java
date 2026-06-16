package com.jexis.jexis_backend.user.application.useCases;

import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.EmailExistsException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

/**
 * CreateUserUseCase
 *
 * This service class implements the use case for creating a new user.
 * It contains only the business logic related to user creation, such as
 * validating input data and interacting with the repository to persist the new
 * user.
 *
 * Author: Leo
 */
@Service
public class CreateUserUseCase {
    private final UserRepository repo;
    private final AsyncLogger logger;
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    public CreateUserUseCase(UserRepository repo, AsyncLogger logger) {
        this.repo = repo;
        this.logger = logger;
    }

    /*
     * Creates a new user
     *
     * Accepts a {@link CreateDto} payload from controller, creates a new user,
     * and returns {@link User} with the new user.
     *
     * @param body the create data transfer object containing email and password
     * 
     * @return a {@link User} containing the new user
     */
    public User execute(CreateDto body) {
        logger.info("USER", "Creating user for email: " + body.getEmail());

        Optional<User> existingUser = repo.findByEmail(body.getEmail());

        if (existingUser.isPresent()) {
            logger.info("USER", "User creation failed: email already exists " + body.getEmail());
            throw new EmailExistsException();
        }

        body.setPassword(argon.encode(body.getPassword()));

        User savedUser = repo
                .save(new User(body.getFirstName(), body.getLastName(), body.getEmail(), body.getPassword()));
        logger.info("USER", "User created successfully: " + savedUser.getEmail());
        return savedUser;
    }
}