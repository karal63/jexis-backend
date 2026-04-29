package com.jexis.jexis_backend.account.presentation;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.application.useCases.CreateAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.DeleteAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AccountController
 *
 * REST controller in the presentation layer responsible for exposing
 * account-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated account use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    public AccountController(CreateAccountUseCase createAccount, DeleteAccountUseCase deleteAccount) {
        this.createAccountUseCase = createAccount;
        this.deleteAccountUseCase = deleteAccount;
    }

    /**
     * Handles account creation requests.
     *
     * Accepts a {@link CreateAccountDto} payload, delegates execution to the
     * createAccountUseCase, and returns the created {@link Account}.
     *
     * Endpoint: POST /account/create
     *
     * @param body request payload containing account creation data
     * @return the newly created account
     */
    @PostMapping("/create")
    public Account create(@RequestBody CreateAccountDto body) {
        return createAccountUseCase.execute(body);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        deleteAccountUseCase.execute(id);
        return "Account with ID " + id + " has been deleted.";
    }

    @PatchMapping("/edit")
    public String edit() {
        return "Edit account endpoint";
    }
}