package com.jexis.jexis_backend.wallet.application.dto;

/**
 * EditWalletDto
 *
 * Data Transfer Object used for wallet editing requests. It encapsulates the
 * necessary data required to edit an existing wallet, such as the wallet name
 * and
 * owner ID.
 *
 * Author: Leo
 */
public class EditWalletDto {
    private String name;

    public String getName() {
        return name;
    }

}
