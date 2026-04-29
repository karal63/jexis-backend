package com.jexis.jexis_backend.account.domain.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND.value(), "ACCOUNT_NOT_FOUND", "Account with id " + id + " not found");
    }
}
