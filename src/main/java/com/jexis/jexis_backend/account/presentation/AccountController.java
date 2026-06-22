package com.jexis.jexis_backend.account.presentation;

import java.util.List;
import java.util.Optional;
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
import com.jexis.jexis_backend.account.application.useCases.GetAccountCardHoldersUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountCardsUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountMembersUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountWalletsUseCase;
import com.jexis.jexis_backend.account.application.useCases.GetAccountsUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.card.application.dto.CardResponseDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.cardholder.application.dto.CardHolderResponseDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.member.application.dto.MemberResponseDto;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.wallet.application.dto.WalletResponseDto;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

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
    private final GetAccountUseCase getAccountUseCase;
    private final EditAccountUseCase editAccountUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DtoHelper dtoHelper;
    private final GetAccountCardsUseCase getAccountCardsUseCase;
    private final GetAccountCardHoldersUseCase getAccountCardHoldersUseCase;
    private final GetAccountMembersUseCase getAccountMembersUseCase;
    private final GetAccountWalletsUseCase getAccountWalletsUseCase;

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
            GetAccountWalletsUseCase getAccountWalletsUseCase) {
        this.createAccountUseCase = createAccount;
        this.deleteAccountUseCase = deleteAccount;
        this.getAccountsUseCase = getAccounts;
        this.getAccountUseCase = getAccount;
        this.editAccountUseCase = editAccount;
        this.getUserUseCase = getUserUseCase;
        this.dtoHelper = dtoHelper;
        this.getAccountCardsUseCase = getAccountCardsUseCase;
        this.getAccountCardHoldersUseCase = getAccountCardHoldersUseCase;
        this.getAccountMembersUseCase = getAccountMembersUseCase;
        this.getAccountWalletsUseCase = getAccountWalletsUseCase;
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
    // ADMIN GLOBAL
    @GetMapping("/list")
    public List<AccountResponseDto> getAll() {
        List<Account> accounts = getAccountsUseCase.execute();
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
    @GetMapping("/list/{id}")
    public AccountResponseDto get(@PathVariable UUID id) {
        Account account = getAccountUseCase.execute(id);
        return dtoHelper.toAccountDto(account);
    }

    @GetMapping("/list/{id}/cards")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public List<CardResponseDto> getCardsByAccount(@PathVariable UUID id) {
        List<Card> card = getAccountCardsUseCase.execute(id);
        return card.stream().map(dtoHelper::toCardDto).toList();
    }

    @GetMapping("/list/{id}/card-holders")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public List<CardHolderResponseDto> getCardHoldersByAccount(@PathVariable UUID id) {
        List<CardHolder> cardHolder = getAccountCardHoldersUseCase.execute(id);
        return cardHolder.stream().map(dtoHelper::toCardHolderDto).toList();
    }

    @GetMapping("/list/{id}/members")
    @PreAuthorize("@canAccessUseCase.execute(authentication.principal.id(), #id)")
    public List<MemberResponseDto> getMembersByAccount(@PathVariable UUID id) {
        List<Member> member = getAccountMembersUseCase.execute(id);
        return member.stream().map(dtoHelper::toMemberDto).toList();
    }

    @GetMapping("/list/{id}/wallets")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public List<WalletResponseDto> getWalletsByAccount(@PathVariable UUID id) {
        List<Wallet> wallet = getAccountWalletsUseCase.execute(id);
        return wallet.stream().map(dtoHelper::toWalletDto).toList();
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
    @DeleteMapping("/delete/{accountId}")
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
    @PatchMapping("/edit/{accountId}")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public AccountResponseDto edit(@PathVariable UUID accountId, @RequestBody EditAccountDto body) {
        return editAccountUseCase.execute(accountId, body);
    }

}