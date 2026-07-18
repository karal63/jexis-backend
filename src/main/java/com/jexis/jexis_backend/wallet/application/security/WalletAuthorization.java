package com.jexis.jexis_backend.wallet.application.security;

import java.util.UUID;

import com.jexis.jexis_backend.transaction.application.useCases.GetTransactionUseCase;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.jexis.jexis_backend.member.application.useCases.HasRoleUseCase;
import com.jexis.jexis_backend.member.domain.enums.Role;

@Component
@RequiredArgsConstructor
public class WalletAuthorization {
    private final HasRoleUseCase hasRoleUseCase;
    private final GetWalletUseCase getWalletUseCase;
    private final GetTransactionUseCase getTransactionUseCase;

    public boolean canView(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN);
    }

    public boolean canView(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return (hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                || hasRoleUseCase.execute(userId, accountId, Role.ADMIN))
                && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canCreate(UUID userId, UUID accountId) {
        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }

    public boolean canEdit(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER) && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canClose(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER) && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canAddMoney(UUID userId, UUID accountId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER)
                && wallet.getAccount().getId().equals(accountId);
    }

    public boolean canCreateOutboundTransfers(UUID userId, UUID walletId) {
        Wallet wallet = getWalletUseCase.execute(walletId);
        UUID accountId = wallet.getAccount().getId();

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }

    public boolean canCancelOutboundTransfers(UUID userId, UUID transactionId) {
        Transaction transaction = getTransactionUseCase.execute(transactionId);
        UUID accountId = transaction.getWallet().getAccount().getId();

        return hasRoleUseCase.execute(userId, accountId, Role.OWNER);
    }
}
