package com.jexis.jexis_backend.account.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.application.useCases.CreateAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.DeleteAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountsUseCase;
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
    private final GetAccountsUseCase getAccountsUseCase;

    public AccountController(
            CreateAccountUseCase createAccount,
            DeleteAccountUseCase deleteAccount,
            GetAccountsUseCase getAccounts) {

        this.createAccountUseCase = createAccount;
        this.deleteAccountUseCase = deleteAccount;
        this.getAccountsUseCase = getAccounts;
    }

    /**
     * Returns a list of all accounts.
     *
     * This endpoint retrieves all existing accounts by delegating to the
     * getAccountsUseCase, which interacts with the repository to fetch the data.
     *
     * Endpoint: GET /account/list
     *
     * @return list of all accounts
     */
    @GetMapping("/list")
    public List<Account> get() {
        return getAccountsUseCase.execute();
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

    /**
     * Handles account deletion requests.
     *
     * Accepts a {@link id} in the path, delegates execution to the
     * deleteAccountUseCase, and returns the deleted {@link Account}.
     *
     * Endpoint: DELETE /account/delete/{id}
     *
     * @param id the ID of the account to delete
     * @return message confirming deletion of the account with the specified ID
     */
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