package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.application.dto.EditWalletDto;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.domain.exceptions.WalletNotFoundException;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

@Service
public class EditWalletUseCase {
    WalletRepository repo;

    public EditWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    public Wallet execute(UUID id, EditWalletDto dto) {

        Optional<Wallet> wallet = repo.findById(id);

        if (!wallet.isPresent()) {
            throw new WalletNotFoundException();
        }

        wallet.ifPresent(newWallet -> {
            if (dto.getAvailableBalance() != null) {
                newWallet.setAvailableBalance(dto.getAvailableBalance());
            }
            if (dto.getIsDeleted() != null) {
                newWallet.setIsDeleted(dto.getIsDeleted());
            }
            if (dto.getDeletedAt() != null) {
                newWallet.setDeletedAt(dto.getDeletedAt());
            }
            repo.save(newWallet);
        });

        return wallet.get();
    }
}
