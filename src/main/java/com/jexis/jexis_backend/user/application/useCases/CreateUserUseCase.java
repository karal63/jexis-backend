package com.jexis.jexis_backend.user.application.useCases;

import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.EmailExistsException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class CreateUserUseCase {
    private final UserRepository repo;
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    public CreateUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public User execute(CreateDto body) {
        Optional<User> existingUser = repo.findByEmail(body.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailExistsException();
        }

        body.setPassword(argon.encode(body.getPassword()));

        return repo.save(new User(body.getName(), body.getEmail(), body.getPassword()));
    }
}