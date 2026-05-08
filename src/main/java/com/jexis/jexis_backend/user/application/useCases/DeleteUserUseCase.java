package com.jexis.jexis_backend.user.application.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.user.domain.exceptions.UserNotFoundException;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

@Service
public class DeleteUserUseCase {
    UserRepository repo;

    public DeleteUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(UUID id) {
        if (!repo.existsById(id)) {
            throw new UserNotFoundException();
        }
        repo.deleteById(id);
    }
}