package com.jexis.jexis_backend.user.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.user.application.dto.EditDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.domain.exceptions.UserNotFoundException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class EditUserUseCase {
    UserRepository repo;

    public EditUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> execute(UUID id, EditDto body) {
        Optional<User> foundUser = repo.findById(id);

        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }

        foundUser.ifPresent(user -> {
            if (body.getName() != null) {
                user.setName(body.getName());
            }
            if (body.getEmail() != null) {
                user.setEmail(body.getEmail());
            }
            if (body.getPassword() != null) {
                user.setPassword(body.getPassword());
            }
            repo.save(user);
        });

        return foundUser;
    }
}