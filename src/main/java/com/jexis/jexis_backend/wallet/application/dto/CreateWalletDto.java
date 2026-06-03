package com.jexis.jexis_backend.wallet.application.dto;

import java.util.UUID;

/**
 * CreateWalletDto
 *
 * Data Transfer Object used for wallet creation requests. It encapsulates the
 * necessary data required to create a new wallet, such as the wallet name and
 * owner ID.
 *
 * Author: Leo
 */
public class CreateWalletDto {
    private UUID accountId;

    public UUID getAccountId() {
        return accountId;
    }
}
