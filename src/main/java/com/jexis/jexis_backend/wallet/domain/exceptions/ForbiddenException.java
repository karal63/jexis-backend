package com.jexis.jexis_backend.wallet.domain.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        super("You don't have permission to access this resource");
    }
}
