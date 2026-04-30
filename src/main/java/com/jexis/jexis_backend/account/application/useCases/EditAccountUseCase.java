package com.jexis.jexis_backend.account.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.application.dto.EditAccountDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.domain.exception.AccountNotFoundException;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

/**
 * EditAccountUseCase
 *
 * This service class implements the use case for editing an existing account.
 * It contains the business logic related to updating an account, such as
 * interacting with the repository to fetch and save the updated data, and
 * validating if user with given id exists.
 *
 * Author: Leo
 */
@Service
public class EditAccountUseCase {
    AccountRepository repo;

    public EditAccountUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    /**
     * Handles account editing.
     *
     * Accepts a {@link EditAccountDto} payload and {@link id} from controller,
     * looks for
     * the account, calls the repository to update the account, and returns
     * the updated {@link Account}.
     *
     * @param body passed by controller payload containing account editing data
     * @param id   the ID of the account to edit
     * @return the updated account
     */
    public Optional<Account> execute(UUID id, EditAccountDto body) {
        Optional<Account> account = repo.findById(id);

        if (account.isEmpty()) {
            throw new AccountNotFoundException(id);
        }

        account = account.map(existingAccount -> {
            if (body.getName() != null) {
                existingAccount.setName(body.getName());
            }
            if (body.getOwnerId() != null) {
                existingAccount.setOwnerId(body.getOwnerId());
            }
            return repo.save(existingAccount);
        });

        return account;
    }
}
