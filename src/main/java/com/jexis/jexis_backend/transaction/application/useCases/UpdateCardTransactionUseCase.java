package com.jexis.jexis_backend.transaction.application.useCases;

import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import com.jexis.jexis_backend.dispute.infrastructure.DisputeRepository;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateCardTransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final DisputeRepository disputeRepository;

    public void execute(com.stripe.model.issuing.Transaction issuingTransaction) {
        Optional<Transaction> transaction = transactionRepository.findByStripeObjectId(issuingTransaction.getId());
        Optional<Dispute> dispute = disputeRepository.findByStripeDisputeId(issuingTransaction.getDispute());

        if (transaction.isPresent()) {
            transaction.get().setAmount(issuingTransaction.getAmount());
            transaction.get().setCurrency(issuingTransaction.getCurrency());
            transaction.get().setMerchantName(issuingTransaction.getMerchantData().getName());
            transaction.get().setMerchantCategory(issuingTransaction.getMerchantData().getCategory());
            transaction.get().setMerchantCountry(issuingTransaction.getMerchantData().getCountry());
            transaction.get().setMerchantCity(issuingTransaction.getMerchantData().getCity());
            transaction.get().setDispute(dispute.orElse(null));

            transactionRepository.save(transaction.get());
        } else {
            System.out.println("Transaction that you wanted to update doesnt exist");
        }
    }
}
