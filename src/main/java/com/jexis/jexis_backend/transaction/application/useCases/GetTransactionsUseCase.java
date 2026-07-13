package com.jexis.jexis_backend.transaction.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;

/**
 * GetTransactionsUseCase
 *
 * This service class implements the use case for retrieving all transactions.
 * It contains only the business logic related to fetching transactions from
 * the repository.
 *
 * Author: Copilot
 */
@Service
public class GetTransactionsUseCase {

    private final TransactionRepository repo;

    public GetTransactionsUseCase(TransactionRepository transactionRepository) {
        this.repo = transactionRepository;
    }

    /**
     * Handles fetching all transactions.
     * Calls the repository to fetch all transactions and returns the list.
     *
     * @return list of all transactions
     */
    public List<Transaction> execute() {
        return repo.findAll();
    }
}
