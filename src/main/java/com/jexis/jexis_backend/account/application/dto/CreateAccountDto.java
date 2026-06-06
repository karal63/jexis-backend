package com.jexis.jexis_backend.account.application.dto;

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
    private String email;

    public String getEmail() {
        return email;
    }
}
