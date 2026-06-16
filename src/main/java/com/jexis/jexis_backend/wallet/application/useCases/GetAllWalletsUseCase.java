package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

/**
 * GetAllWalletsUseCase
 *
 * This service class implements the use case for retrieving all wallets.
 * It contains only the business logic related to fetching wallet data, such as
 * interacting with the repository to retrieve the list of wallets.
 *
 * Author: Leo
 */
@Service
public class GetAllWalletsUseCase {
    WalletRepository repo;

    public GetAllWalletsUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    /*
     * Retrieves all wallets
     *
     * Interacts with the repository to fetch the list of all wallets.
     *
     * @return a list of all wallets
     */
    public List<Wallet> execute() {
        return repo.findAll();
    }
}
