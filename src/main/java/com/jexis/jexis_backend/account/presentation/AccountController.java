package com.jexis.jexis_backend.account.presentation;

import java.util.List;
import java.util.UUID;

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
import com.jexis.jexis_backend.account.application.useCases.CreateAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.DeleteAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.EditAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountsUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetUserAccountsUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.card.application.useCases.GetAccountCardsUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetAccountCardHoldersUseCase;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.member.application.useCases.GetAccountMembersUseCase;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.wallet.application.useCases.GetAccountWalletsUseCase;

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

    public AccountController(
            CreateAccountUseCase createAccount,
            DeleteAccountUseCase deleteAccount,
            GetAccountsUseCase getAccounts,
            GetAccountUseCase getAccount,
            EditAccountUseCase editAccount, GetUserUseCase getUserUseCase,
            DtoHelper dtoHelper,
            GetAccountCardsUseCase getAccountCardsUseCase,
            GetAccountCardHoldersUseCase getAccountCardHoldersUseCase,
            GetAccountMembersUseCase getAccountMembersUseCase,
            GetAccountWalletsUseCase getAccountWalletsUseCase,
            GetUserAccountsUseCase getUserAccountsUseCase) {
        this.createAccountUseCase = createAccount;
        this.deleteAccountUseCase = deleteAccount;
        this.getAccountsUseCase = getAccounts;
        this.getAccountUseCase = getAccount;
        this.editAccountUseCase = editAccount;
        this.getUserUseCase = getUserUseCase;
        this.dtoHelper = dtoHelper;
        this.getUserAccountsUseCase = getUserAccountsUseCase;
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

    @GetMapping("/account/list")
    // @PreAuthorize("@hasRole('ADMIN')")
    public List<AccountResponseDto> getAll() {
        List<Account> accounts = getAccountsUseCase.execute();
        return accounts
                .stream()
                .map(dtoHelper::toAccountDto)
                .toList();
    }

    @GetMapping("/users/{id}/accounts")
    public List<AccountResponseDto> getUserAccounts(@PathVariable UUID id) {
        List<Account> accounts = getUserAccountsUseCase.execute(id);
        return accounts
                .stream()
                .map(dtoHelper::toAccountDto)
                .toList();
    }

    /**
     * Return a specific account.
     *
     * This endpoint retrieves a specific account by delegating to the
     * getAccountsUseCase, which interacts with the repository to fetch the data.
     *
     * Endpoint: GET /account/{id}
     *
     * @param id the ID of the account to retrieve
     * @return the account with the specified ID
     */
    @GetMapping("/users/{id}/accounts/{accountId}")
    @PreAuthorize("@canAccessUseCase.execute(authentication.principal.id(), #accountId)")
    public AccountResponseDto get(@PathVariable UUID accountId) {
        Account account = getAccountUseCase.execute(accountId);
        return dtoHelper.toAccountDto(account);
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
    @PostMapping("/account/create")
    public AccountResponseDto create(@AuthenticationPrincipal AuthUser user) {
        User foundUser = getUserUseCase.execute(user.id());
        return createAccountUseCase.execute(foundUser);
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
    @DeleteMapping("/account/{accountId}/delete")
    @PreAuthorize("@hasRoleUseCase.execute(authentication.principal.id(), #accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)")
    public String delete(@PathVariable UUID id) {
        deleteAccountUseCase.execute(id);
        return "Account with ID " + id + " has been deleted.";
    }

    /**
     * Handles account editing requests.
     *
     * Accepts a {@link id} in the path, delegates execution to the
     * editAccountUseCase, and returns the updated {@link Account}.
     *
     * Endpoint: PATCH /account/edit/{id}
     *
     * @param id   the ID of the account to edit
     * @param body payload with updated values for the account
     * @return retuers updated account
     */
    @PatchMapping("/account/{accountId}/edit")
    @PreAuthorize("@hasRoleUseCase.execute(authentication.principal.id(), #accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)")
    public AccountResponseDto edit(@PathVariable UUID accountId, @RequestBody EditAccountDto body) {
        return editAccountUseCase.execute(accountId, body);
    }

}