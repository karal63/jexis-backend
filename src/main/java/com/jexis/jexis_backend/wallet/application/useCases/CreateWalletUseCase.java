package com.jexis.jexis_backend.wallet.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

/**
 * CreateWalletUseCase
 *
 * This service class implements the use case for creating a new wallet.
 * It contains only the business logic related to wallet creation, such as
 * interacting with the repository to persist the new wallet.
 *
 * Author: Leo
 */
@Service
public class CreateWalletUseCase {
    WalletRepository repo;

    public CreateWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    /*
     * Creates a new wallet
     *
     * Accepts a {@link CreateDto} payload from controller, creates a new wallet,
     * and returns the created wallet.
     *
     * @param body the create data transfer object containing wallet details
     * 
     * @return the created wallet
     */
    public Wallet execute(Account account) {
        Wallet wallet = new Wallet(account);
        return repo.save(wallet);
    }
}
