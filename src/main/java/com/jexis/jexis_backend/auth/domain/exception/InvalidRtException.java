package com.jexis.jexis_backend.auth.domain.exception;

import org.springframework.http.HttpStatus;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class InvalidRtException extends DomainException {
    public InvalidRtException() {
        super(HttpStatus.BAD_REQUEST.value(), "INVALID_RT", "Invalid refresh token");
    }
}
