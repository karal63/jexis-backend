package com.jexis.jexis_backend.card.domain.exceptions;

import org.springframework.http.HttpStatus;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class CardNotFoundException extends DomainException {
    public CardNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "CARD_NOT_FOUND", "Card not found");
    }
}
