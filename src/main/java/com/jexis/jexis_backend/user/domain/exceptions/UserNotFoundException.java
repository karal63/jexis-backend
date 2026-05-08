package com.jexis.jexis_backend.user.domain.exceptions;

import org.springframework.http.HttpStatus;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", "User not found");
    }
}
