package com.jexis.jexis_backend.account.application.useCases;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;

/**
 * CreateAccountUseCase
 *
 * This service class implements the use case for creating a new account.
 * It contains only the business logic related to account creation, such as
 * validating input data and interacting with the repository to persist the new
 * account.
 *
 * Author: Leo
 */
@Service
public class CreateAccountUseCase {
    private final AccountRepository repo;

    public CreateAccountUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    /**
     * Handles account creation.
     *
     * Accepts a {@link CreateAccountDto} payload from controller, looks for
     * duplicate account name, calls the repository to save the account, and returns
     * the created {@link Account}.
     *
     * @param body passed by controller payload containing account creation data
     * @return the newly created account
     */
    public Account execute(CreateAccountDto body) {
        Account existingAccount = repo.findByName(body.getName());
        if (existingAccount != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account already exists"); // LogCode.A009
        }

        Account account = new Account(body.getName(), body.getOwnerId());
        return repo.save(account);
    }
}
