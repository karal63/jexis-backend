package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class GetAllWalletsUseCase {
    WalletRepository repo;

    public GetAllWalletsUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    public List<Wallet> execute() {
        return repo.findAll();
    }
}
