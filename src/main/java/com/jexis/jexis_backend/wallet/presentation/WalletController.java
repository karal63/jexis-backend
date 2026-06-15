package com.jexis.jexis_backend.wallet.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
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
import com.jexis.jexis_backend.stripe.application.dto.CreateTreasuryAccountDto;
import com.jexis.jexis_backend.wallet.application.dto.CreateWalletDto;
import com.jexis.jexis_backend.wallet.application.dto.EditWalletDto;
import com.jexis.jexis_backend.wallet.application.useCases.CreateWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.DeleteWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.EditWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetAllWalletsUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.stripe.exception.StripeException;

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

    GetAllWalletsUseCase getAllWalletsUseCase;
    GetAccountUseCase getAccountUseCase;
    CreateWalletUseCase createWalletUseCase;
    GetWalletUseCase getWalletUseCase;
    EditWalletUseCase editWalletUseCase;
    DeleteWalletUseCase deleteWalletUseCase;

    public WalletController(GetAllWalletsUseCase getAllWalletsUseCase, GetAccountUseCase getAccountUseCase,
            CreateWalletUseCase createWalletUseCase, GetWalletUseCase getWalletUseCase,
            EditWalletUseCase editWalletUseCase, DeleteWalletUseCase deleteWalletUseCase) {
        this.getAllWalletsUseCase = getAllWalletsUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.createWalletUseCase = createWalletUseCase;
        this.getWalletUseCase = getWalletUseCase;
        this.editWalletUseCase = editWalletUseCase;
        this.deleteWalletUseCase = deleteWalletUseCase;
    }

    /**
     * Retrieves all wallets available in the system.
     *
     * Endpoint: GET /wallet/list
     *
     * @return a list of all wallet entities
     */
    @GetMapping("/list")
    public List<Wallet> list() {
        return getAllWalletsUseCase.execute();
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
    public Wallet find(@PathVariable UUID id) {
        return getWalletUseCase.execute(id);
    }

    /**
     * Creates a new wallet for the specified account.
     *
     * Endpoint: POST /wallet/create
     *
     * @param body the request payload containing the account identifier
     * @return the newly created wallet entity
     */
    @PostMapping("/create-treasury-account")
    public Wallet create(@RequestBody CreateWalletDto body) throws StripeException {
        Account account = getAccountUseCase.execute(body.getAccountId());
        return createWalletUseCase.execute(account);
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
    public Wallet edit(@PathVariable UUID id, @RequestBody EditWalletDto body) {
        return editWalletUseCase.execute(id, body);
    }

    /**
     * Deletes a wallet owned by the authenticated user.
     *
     * Endpoint: POST /wallet/delete/{id}
     *
     * @param user the authenticated user making the request
     * @param id   the unique identifier of the wallet to delete
     */
    @PostMapping("/delete/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteWalletUseCase.execute(user, id);
    }

    // public ResponseEntity<String> createTreasuryAccount(@RequestBody
    // CreateTreasuryAccountDto body)
    // throws StripeException {
    // try {
    // return ResponseEntity.ok("Treasury account created");
    // } catch (Exception e) {
    // return ResponseEntity.status(500).body("Error creating treasury account: " +
    // e.getMessage());
    // }
    // }
}
