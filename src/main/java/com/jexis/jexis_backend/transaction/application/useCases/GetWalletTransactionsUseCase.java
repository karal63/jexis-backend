package com.jexis.jexis_backend.transaction.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;

/**
 * GetWalletTransactionsUseCase
 *
 * This service class implements the use case for retrieving all transactions
 * associated with a specific wallet. It contains only the business logic related
 * to fetching transactions filtered by wallet ID from the repository.
 *
 * Author: Copilot
 */
@Service
public class GetWalletTransactionsUseCase {

    private final TransactionRepository repo;

    public GetWalletTransactionsUseCase(TransactionRepository transactionRepository) {
        this.repo = transactionRepository;
    }

    /**
     * Handles fetching all transactions for a specific wallet.
     * Calls the repository to fetch transactions filtered by wallet ID.
     *
     * @param walletId the unique identifier of the wallet
     * @return list of transactions for the wallet
     */
    public List<Transaction> execute(UUID walletId) {
        return repo.findByWalletId(walletId);
    }
}
