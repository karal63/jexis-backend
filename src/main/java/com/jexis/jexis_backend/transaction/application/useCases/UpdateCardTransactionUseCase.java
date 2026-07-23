package com.jexis.jexis_backend.transaction.application.useCases;

import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCardTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public void execute(com.stripe.model.issuing.Transaction issuingTransaction) {
        Optional<Transaction> transaction = transactionRepository.findByStripeObjectId(issuingTransaction.getId());

        if (transaction.isPresent()) {
            transaction.get().setAmount(issuingTransaction.getAmount());
            transaction.get().setCurrency(issuingTransaction.getCurrency());
//            transaction.get().setStatus(issuingTransaction.);
        } else {
            System.out.println("Transaction that you wanted to update doesnt exist");
        }
    }
}
