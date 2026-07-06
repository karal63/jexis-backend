package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class GetAccountWalletsUseCase {
    private final WalletRepository walletRepo;

    public GetAccountWalletsUseCase(WalletRepository walletRepo) {
        this.walletRepo = walletRepo;
    }

    public List<Wallet> execute(UUID accountId) {
        return walletRepo.findAllByAccountIdAndIsDeletedFalse(accountId);
    }
}
