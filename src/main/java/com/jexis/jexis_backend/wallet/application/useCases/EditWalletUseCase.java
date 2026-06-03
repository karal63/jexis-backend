package com.jexis.jexis_backend.wallet.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.wallet.application.dto.EditWalletDto;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.domain.exceptions.WalletNotFoundException;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;

/**
 * EditWalletUseCase
 *
 * This service class implements the use case for editing a wallet.
 * It contains only the business logic related to wallet modification, such as
 * validating input data and interacting with the repository to update the
 * wallet.
 *
 * Author: Leo
 */
@Service
public class EditWalletUseCase {
    WalletRepository repo;

    public EditWalletUseCase(WalletRepository repo) {
        this.repo = repo;
    }

    /*
     * Edits a wallet
     *
     * Accepts a {@link EditWalletDto} payload from controller, validates the
     * request,
     * and updates the wallet with the new information.
     *
     * @param id the ID of the wallet to edit
     * 
     * @param dto the edit data transfer object containing updated wallet details
     * 
     * @return the updated wallet
     */
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
