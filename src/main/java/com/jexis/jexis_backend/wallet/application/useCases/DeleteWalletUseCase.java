package com.jexis.jexis_backend.wallet.application.useCases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.domain.exceptions.ForbiddenException;
import com.jexis.jexis_backend.wallet.domain.exceptions.WalletNotFoundException;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class DeleteWalletUseCase {
    private final WalletRepository repo;

    public DeleteWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    public void execute(AuthUser user, UUID walletId) {
        Optional<Wallet> wallet = repo.findById(walletId);

        if (!wallet.isPresent()) {
            throw new WalletNotFoundException();
        }

        if (!wallet.get().getAccount().getOwner().getId().equals(user.id())) {
            throw new ForbiddenException();
        }

        wallet.get().setIsDeleted(true);
        wallet.get().setDeletedAt(LocalDateTime.now());
        repo.save(wallet.get());
    }
}
