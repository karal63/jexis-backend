package com.jexis.jexis_backend.wallet.domain.exceptions;

import org.springframework.http.HttpStatus;

import com.jexis.jexis_backend.common.web.error.DomainException;

public class WalletNotFoundException extends DomainException {
    public WalletNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "WALLET_NOT_FOUND", "Wallet not found");
    }

}
