package com.jexis.jexis_backend.transaction.application.useCases;

import com.jexis.jexis_backend.stripe.application.useCases.GetStripeTransactionUseCase;
import com.jexis.jexis_backend.transaction.application.dto.CreateBankTransactionDto;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletByFAIdUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import org.springframework.stereotype.Service;

@Service
public class CreateBankTransactionUseCase {
    private final GetStripeTransactionUseCase getStripeTransactionUseCase;
    private final GetWalletByFAIdUseCase getWalletByFAIdUseCase;
    private final TransactionRepository repo;

    public CreateBankTransactionUseCase(GetStripeTransactionUseCase getStripeTransactionUseCase, GetWalletByFAIdUseCase getWalletByFAIdUseCase,
                                        TransactionRepository repo) {
        this.getStripeTransactionUseCase = getStripeTransactionUseCase;
        this.getWalletByFAIdUseCase = getWalletByFAIdUseCase;
        this.repo = repo;
    }

    public void execute(CreateBankTransactionDto dto) {
        com.stripe.model.treasury.Transaction treasuryTransaction = getStripeTransactionUseCase.execute(dto.accountId(), dto.transactionId());

        Wallet wallet = getWalletByFAIdUseCase.execute(dto.financialAccountId());

        Transaction transaction = new Transaction(
                wallet,
                treasuryTransaction.getId(),
                dto.stripeObjectId(),
                dto.type(),
                treasuryTransaction.getAmount(),
                treasuryTransaction.getCurrency(),
                dto.status(),
                dto.direction()
        );

        transaction.setBankName(dto.bankName());
        transaction.setBankAccountLast4(dto.bankAccountLast4());
        transaction.setRoutingNumber(dto.routingNumber());
        transaction.setPaymentMethod(dto.paymentMethod());

        repo.save(transaction);
    }
}
