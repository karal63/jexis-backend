package com.jexis.jexis_backend.transaction.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.transaction.application.dto.TransactionResponseDto;
import com.jexis.jexis_backend.transaction.application.useCases.GetTransactionUseCase;
import com.jexis.jexis_backend.transaction.application.useCases.GetTransactionsUseCase;
import com.jexis.jexis_backend.transaction.application.useCases.GetWalletTransactionsUseCase;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;

/**
 * TransactionController
 * <p>
 * REST controller in the presentation layer responsible for exposing
 * transaction-related HTTP endpoints.
 * <p>
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated transaction use case
 * services (application layer).
 * <p>
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 * <p>
 * Author: Copilot
 */
@RestController
@RequestMapping("/")
public class TransactionController {

    private final GetTransactionUseCase getTransactionUseCase;
    private final GetTransactionsUseCase getTransactionsUseCase;
    private final GetWalletTransactionsUseCase getWalletTransactionsUseCase;
    private final DtoHelper dtoHelper;

    public TransactionController(
            GetTransactionUseCase getTransactionUseCase,
            GetTransactionsUseCase getTransactionsUseCase,
            GetWalletTransactionsUseCase getWalletTransactionsUseCase,
            DtoHelper dtoHelper) {
        this.getTransactionUseCase = getTransactionUseCase;
        this.getTransactionsUseCase = getTransactionsUseCase;
        this.getWalletTransactionsUseCase = getWalletTransactionsUseCase;
        this.dtoHelper = dtoHelper;
    }

    /**
     * Retrieves all transactions.
     * Endpoint: GET /admin/transactions
     *
     * @return list of all transactions
     */
    @GetMapping("/admin/transactions")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<TransactionResponseDto> getAllTransactions() {
        return getTransactionsUseCase.execute().stream()
                .map(dtoHelper::toTransactionDto)
                .toList();
    }

    /**
     * Retrieves a single transaction by its identifier.
     * Endpoint: GET /transactions/{id}
     *
     * @param id the unique identifier of the transaction to retrieve
     * @return the matching transaction entity
     */
    @GetMapping("/transactions/{id}")
    @PreAuthorize("@transactionAuthorization.canView(authentication.principal.id(), #id)")
    public TransactionResponseDto getTransaction(@PathVariable UUID id) {
        Transaction transaction = getTransactionUseCase.execute(id);
        return dtoHelper.toTransactionDto(transaction);
    }

    /**
     * Retrieves all transactions for a specific wallet.
     * Endpoint: GET /wallets/{walletId}/transactions
     *
     * @param walletId the unique identifier of the wallet
     * @return list of transactions for the wallet
     */
    @GetMapping("/wallets/{walletId}/transactions")
    @PreAuthorize("@transactionAuthorization.canViewWallet(authentication.principal.id(), #walletId)")
    public List<TransactionResponseDto> getWalletTransactions(@PathVariable UUID walletId) {
        return getWalletTransactionsUseCase.execute(walletId).stream()
                .map(dtoHelper::toTransactionDto)
                .toList();
    }
}
