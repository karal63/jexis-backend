package com.jexis.jexis_backend.account.application.dto;

import java.util.UUID;

/**
 * CreateAccountDto
 *
 * Data Transfer Object used for account creation requests. It encapsulates the
 * necessary data required to create a new account, such as the account name and
 * owner ID.
 *
 * Author: Leo
 */
public class CreateAccountDto {

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

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
