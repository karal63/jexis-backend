package com.jexis.jexis_backend.account.presentation;

import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.account.application.dto.GetUpdateLinkDto;
import com.jexis.jexis_backend.account.application.useCases.*;
import com.stripe.model.AccountLink;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.account.application.dto.EditAccountDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * AccountController
 * REST controller in the presentation layer responsible for exposing
 * account-related HTTP endpoints.
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated account use case
 * services (application layer).
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 * Author: Leo
 */
@RestController
@RequestMapping("/")
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final GetAccountsUseCase getAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final EditAccountUseCase editAccountUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DtoHelper dtoHelper;
    private final GetUserAccountsUseCase getUserAccountsUseCase;
    private final GetUpdateLinkUseCase getUpdateLinkUseCase;

    public AccountController(
            CreateAccountUseCase createAccount,
            DeleteAccountUseCase deleteAccount,
            GetAccountsUseCase getAccounts,
            GetAccountUseCase getAccount,
            EditAccountUseCase editAccount, GetUserUseCase getUserUseCase,
            DtoHelper dtoHelper,
            GetUserAccountsUseCase getUserAccountsUseCase,
            GetUpdateLinkUseCase getUpdateLinkUseCase) {
        this.createAccountUseCase = createAccount;
        this.deleteAccountUseCase = deleteAccount;
        this.getAccountsUseCase = getAccounts;
        this.getAccountUseCase = getAccount;
        this.editAccountUseCase = editAccount;
        this.getUserUseCase = getUserUseCase;
        this.dtoHelper = dtoHelper;
        this.getUserAccountsUseCase = getUserAccountsUseCase;
        this.getUpdateLinkUseCase = getUpdateLinkUseCase;
    }

    /**
     * Returns a list of all accounts.
     * This endpoint retrieves all existing accounts by delegating to the
     * getAccountsUseCase, which interacts with the repository to fetch the data.
     * Endpoint: GET /admin/accounts
     *
     * @return list of all accounts
     */

    @GetMapping("/admin/accounts")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<AccountResponseDto> getAll() {
        List<Account> accounts = getAccountsUseCase.execute();
        return accounts
                .stream()
                .map(dtoHelper::toAccountDto)
                .toList();
    }

    @GetMapping("/users/{id}/accounts")
    @PreAuthorize("@accountAuthorization.canViewAll(authentication.principal.id(), #id)")
    public List<AccountResponseDto> getUserAccounts(@PathVariable UUID id) {
        List<Account> accounts = getUserAccountsUseCase.execute(id);
        return accounts
                .stream()
                .map(dtoHelper::toAccountDto)
                .toList();
    }

    /**
     * Return a specific account.
     * This endpoint retrieves a specific account by delegating to the
     * getAccountsUseCase, which interacts with the repository to fetch the data.
     * Endpoint: GET /account/{id}
     *
     * @param id  the ID of the user
     * @param accountId the ID of the user
     * @return the account with the specified ID
     */
    @GetMapping("/users/{id}/accounts/{accountId}")
    @PreAuthorize("@accountAuthorization.canView(authentication.principal.id(), #accountId)")
    public AccountResponseDto get(@PathVariable UUID id, @PathVariable UUID accountId) {
        Account account = getAccountUseCase.execute(accountId);
        return dtoHelper.toAccountDto(account);
    }

    /**
     * Handles account creation requests.
     * Accepts a {@link AuthUser} payload, delegates execution to the
     * createAccountUseCase, and returns the created {@link Account}.
     * Endpoint: POST /account/create
     *
     * @param user user principal
     * @return the newly created account
     */
    @PostMapping("/accounts/create")
    public AccountResponseDto create(@AuthenticationPrincipal AuthUser user) {
        User foundUser = getUserUseCase.execute(user.id());
        return createAccountUseCase.execute(foundUser);
    }

    /**
     * Handles account deletion requests.
     * Accepts a {@param id} in the path, delegates execution to the
     * deleteAccountUseCase
     * Endpoint: DELETE /account/delete/{id}
     * @param id the ID of the user account
     * @param accountId the ID of the account to delete
     * @return message confirming deletion of the account with the specified ID
     */
    @DeleteMapping("/users/{id}/accounts/{accountId}/delete")
    @PreAuthorize("@accountAuthorization.canDelete(authentication.principal.id(), #accountId)")
    public String delete(@PathVariable UUID id, @PathVariable UUID accountId) {
        deleteAccountUseCase.execute(accountId);
        return "Account with ID " + accountId + " has been deleted.";
    }

    /**
     * Handles account editing requests.
     * Accepts a {@param id} in the path, delegates execution to the
     * editAccountUseCase, and returns the updated {@link Account}.
     * Endpoint: PATCH /account/edit/{id}
     * @param id   the ID of the account to edit
     * @param body payload with updated values for the account
     * @return returns updated account
     */
    @PatchMapping("/users/{id}/accounts/{accountId}/edit")
    @PreAuthorize("@accountAuthorization.canEdit(authentication.principal.id(), #accountId)")
    public AccountResponseDto edit(@PathVariable UUID id, @PathVariable UUID accountId, @RequestBody EditAccountDto body) {
        return editAccountUseCase.execute(accountId, body);
    }

    @GetMapping("/users/{id}/accounts/{accountId}/get-update-link")
    @PreAuthorize("@accountAuthorization.canEdit(authentication.principal.id(), #accountId)")
    public GetUpdateLinkDto getUpdateLink(@PathVariable UUID id, @PathVariable UUID accountId) {
        AccountLink link = getUpdateLinkUseCase.execute(accountId);
        return new GetUpdateLinkDto(link.getUrl());
    }
}