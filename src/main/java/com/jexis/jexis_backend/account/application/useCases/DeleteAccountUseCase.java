package com.jexis.jexis_backend.account.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.domain.exception.AccountNotFoundException;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

/**
 * DeleteAccountUseCase
 *
 * This service class implements the use case for deleting an existing account.
 * It contains only the business logic related to account deletion, such as
 * validating the input ID and interacting with the repository to remove the
 * account.
 *
 * Author: Leo
 */
@Service
public class DeleteAccountUseCase {
    AccountRepository repo;

    public DeleteAccountUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    public void execute(UUID id) {
        Optional<Account> existingAccount = repo.findById(id);
        if (existingAccount == null) {
            throw new AccountNotFoundException(id);
        }

        repo.deleteById(id);
    }
}
