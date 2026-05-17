package com.jexis.jexis_backend.account.application.dto;

import java.util.UUID;

/**
 * EditAccountDto
 *
 * Data Transfer Object used for account editing requests. It encapsulates the
 * necessary data required to edit an existing account, such as the account ID
 * and updated account details.
 *
 * Author: Leo
 */
public class EditAccountDto {
    private String name;
    private UUID ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

}
