package com.jexis.jexis_backend.transaction.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;

/**
 * GetCardTransactionsUseCase
 *
 * Service for retrieving transactions associated with a specific card.
 */
@Service
public class GetCardTransactionsUseCase {

    private final TransactionRepository repo;

    public GetCardTransactionsUseCase(TransactionRepository transactionRepository) {
        this.repo = transactionRepository;
    }

    public List<Transaction> execute(UUID cardId) {
        return repo.findByCardId(cardId);
    }
}
