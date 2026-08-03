package com.jexis.jexis_backend.wallet.application.useCases;

import com.jexis.jexis_backend.stripe.application.useCases.RetrieveFinancialAccountUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;
import com.stripe.model.treasury.FinancialAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SyncBalanceUseCase {
    private final RetrieveFinancialAccountUseCase retrieveFinancialAccountUseCase;
    private final WalletRepository repo;
    private final GetWalletByFAIdUseCase getWalletByFAIdUseCase;

    public void execute(String accountId, String financialAccountId) {
        FinancialAccount financialAccount = retrieveFinancialAccountUseCase.execute(accountId, financialAccountId);

        Wallet wallet = getWalletByFAIdUseCase.execute(financialAccount.getId());

        wallet.setAvailableBalance(financialAccount.getBalance().getCash().get("usd").intValue());
        repo.save(wallet);
    }
}
