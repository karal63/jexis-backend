package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.domain.exceptions.WalletNotFoundException;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class GetWalletUseCase {
    WalletRepository repo;

    public GetWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    public Wallet execute(UUID id) {
        System.out.println("works");
        Optional<Wallet> wallet = repo.findById(id);
        if (wallet.isEmpty()) {
            System.out.println("error");
            throw new WalletNotFoundException();

        }
        System.out.println("hi");

        return wallet.get();
    }
}
