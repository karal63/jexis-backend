package com.jexis.jexis_backend.card.domain.exceptions;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class ForbiddenException extends DomainException {
    public ForbiddenException() {
        super(403, "FORBIDDEN", "Forbidden");
    }
}
