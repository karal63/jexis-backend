package com.jexis.jexis_backend.transaction.application.useCases;

import com.jexis.jexis_backend.card.application.useCases.GetCardByStripeIdUseCase;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.stripe.application.useCases.GetStripeDebitTransactionUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.GetStripeTransactionUseCase;
import com.jexis.jexis_backend.transaction.application.dto.CreateCardTransactionDto;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.transaction.infrastructure.TransactionRepository;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletByFAIdUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.stripe.model.treasury.ReceivedDebit;
import org.springframework.stereotype.Service;

@Service
public class CreateCardTransactionUseCase {
    private final GetStripeTransactionUseCase getStripeTransactionUseCase;
    private final GetStripeDebitTransactionUseCase getStripeDebitTransactionUseCase;
    private final GetWalletByFAIdUseCase getWalletByFAIdUseCase;
    private final TransactionRepository repo;
    private final GetCardByStripeIdUseCase  getCardByStripeIdUseCase;

    public CreateCardTransactionUseCase(GetWalletByFAIdUseCase getWalletByFAIdUseCase, TransactionRepository repo,  GetStripeTransactionUseCase getStripeTransactionUseCase,
                                        GetCardByStripeIdUseCase  getCardByStripeIdUseCase, GetStripeDebitTransactionUseCase getStripeDebitTransactionUseCase) {
        this.getWalletByFAIdUseCase = getWalletByFAIdUseCase;
        this.repo = repo;
        this.getStripeTransactionUseCase = getStripeTransactionUseCase;
        this.getCardByStripeIdUseCase = getCardByStripeIdUseCase;
        this.getStripeDebitTransactionUseCase = getStripeDebitTransactionUseCase;
    }

    public void execute(CreateCardTransactionDto dto) {
        ReceivedDebit receivedDebit = getStripeDebitTransactionUseCase.execute(dto.accountId(), dto.debitTransactionId());
        com.stripe.model.treasury.Transaction treasuryTransaction = getStripeTransactionUseCase.execute(dto.accountId(), receivedDebit.getTransaction());

        Wallet wallet = getWalletByFAIdUseCase.execute(treasuryTransaction.getFinancialAccount());
        Card card = getCardByStripeIdUseCase.execute(dto.cardId());

        Transaction transaction = new Transaction(
                wallet,
                treasuryTransaction.getId(),
                dto.stripeObjectId(),
                dto.type(),
                dto.amount(),
                dto.currency(),
                dto.status(),
                dto.direction()
        );

        transaction.setCard(card);
        transaction.setMerchantName(dto.merchantName());
        transaction.setMerchantCategory(dto.merchantCategory());
        transaction.setMerchantCity(dto.merchantCity());
        transaction.setMerchantCountry(dto.merchantCountry());

        repo.save(transaction);

    }
}
