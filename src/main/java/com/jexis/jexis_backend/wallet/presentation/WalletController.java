package com.jexis.jexis_backend.wallet.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.wallet.application.dto.CreateWalletDto;
import com.jexis.jexis_backend.wallet.application.dto.EditWalletDto;
import com.jexis.jexis_backend.wallet.application.dto.WalletResponseDto;
import com.jexis.jexis_backend.wallet.application.useCases.CreateWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.DeleteWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.EditWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetAllWalletsUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

import jakarta.validation.Valid;

/**
 * WalletController
 *
 * REST controller in the presentation layer responsible for exposing
 * wallet-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated wallet use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final GetAllWalletsUseCase getAllWalletsUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final CreateWalletUseCase createWalletUseCase;
    private final GetWalletUseCase getWalletUseCase;
    private final EditWalletUseCase editWalletUseCase;
    private final DeleteWalletUseCase deleteWalletUseCase;
    private final DtoHelper dtoHelper;

    public WalletController(GetAllWalletsUseCase getAllWalletsUseCase, GetAccountUseCase getAccountUseCase,
            CreateWalletUseCase createWalletUseCase, GetWalletUseCase getWalletUseCase,
            EditWalletUseCase editWalletUseCase, DeleteWalletUseCase deleteWalletUseCase,
            DtoHelper dtoHelper) {
        this.getAllWalletsUseCase = getAllWalletsUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.createWalletUseCase = createWalletUseCase;
        this.getWalletUseCase = getWalletUseCase;
        this.editWalletUseCase = editWalletUseCase;
        this.deleteWalletUseCase = deleteWalletUseCase;
        this.dtoHelper = dtoHelper;
    }

    /**
     * Retrieves all wallets available in the system.
     *
     * Endpoint: GET /wallet/list
     *
     * @return a list of all wallet entities
     */
    @GetMapping("/list")
    public List<WalletResponseDto> list() {
        List<Wallet> wallets = getAllWalletsUseCase.execute();
        return wallets.stream().map(dtoHelper::toWalletDto).toList();
    }

    /**
     * Retrieves a single wallet by its identifier.
     *
     * Endpoint: GET /wallet/list/{id}
     *
     * @param id the unique identifier of the wallet to retrieve
     * @return the matching wallet entity
     */
    @GetMapping("/list/{id}")
    public WalletResponseDto find(@PathVariable UUID id) {
        Wallet wallet = getWalletUseCase.execute(id);
        return dtoHelper.toWalletDto(wallet);
    }

    /**
     * Creates a new wallet for the specified account.
     *
     * Endpoint: POST /wallet/create
     *
     * @param body the request payload containing the account identifier
     * @return the newly created wallet entity
     */
    // I think only account owner
    @PostMapping("/create-treasury-account")
    @PreAuthorize("@hasRoleUseCase.execute(authentication.principal.id(), #body.accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)")
    public WalletResponseDto create(@Valid @RequestBody CreateWalletDto body) {
        Account account = getAccountUseCase.execute(body.getAccountId());
        Wallet wallet = createWalletUseCase.execute(account, body.getName());
        return dtoHelper.toWalletDto(wallet);
    }

    /**
     * Updates an existing wallet with the provided changes.
     *
     * Endpoint: PATCH /wallet/edit/{id}
     *
     * @param id   the unique identifier of the wallet to update
     * @param body the wallet update payload
     * @return the updated wallet entity
     */
    @PatchMapping("/edit/{id}")
    @PreAuthorize("@walletAuthorization.canEdit(authentication.principal.id(), #id)")
    public WalletResponseDto edit(@PathVariable UUID id, @RequestBody EditWalletDto body) {
        Wallet wallet = editWalletUseCase.execute(id, body);
        return dtoHelper.toWalletDto(wallet);
    }

    /**
     * Deletes a wallet owned by the authenticated user.
     *
     * Endpoint: POST /wallet/delete/{id}
     *
     * @param user the authenticated user making the request
     * @param id   the unique identifier of the wallet to delete
     */
    // I think only account owner
    @PostMapping("/delete/{id}")
    @PreAuthorize("@walletAuthorization.canEdit(authentication.principal.id(), #id)")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteWalletUseCase.execute(user, id);
    }
}
