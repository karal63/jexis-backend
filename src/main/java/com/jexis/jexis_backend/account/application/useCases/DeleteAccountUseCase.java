package com.jexis.jexis_backend.account.application.useCases;

import java.time.LocalDateTime;
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

    /**
     * Handles account deletion.
     *
     * Accepts a {@link UUID} payload from controller, looks for
     * the account, calls the repository to delete the account, and returns
     * nothing.
     *
     * @param body passed by controller payload containing account creation data
     */
    public void execute(UUID id) {
        Account existingAccount = repo.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        existingAccount.setIsDeleted(true);
        existingAccount.setDeletedAt(LocalDateTime.now());

        repo.save(existingAccount);
    }
}
