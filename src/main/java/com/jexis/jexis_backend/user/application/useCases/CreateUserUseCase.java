package com.jexis.jexis_backend.user.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.EmailExistsException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class CreateUserUseCase {
    UserRepository repo;

    public CreateUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public User execute(CreateDto body) {
        User existingUser = repo.findByEmail(body.getEmail());
        if (existingUser != null) {
            throw new EmailExistsException();
        }

        return repo.save(new User(body.getName(), body.getEmail(), body.getPassword()));
    }
}