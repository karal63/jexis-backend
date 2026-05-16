package com.jexis.jexis_backend.wallet.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class CreateWalletUseCase {
    WalletRepository repo;

    public CreateWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    public Wallet execute(Account account) {
        Wallet wallet = new Wallet(account);
        return repo.save(wallet);
    }
}
