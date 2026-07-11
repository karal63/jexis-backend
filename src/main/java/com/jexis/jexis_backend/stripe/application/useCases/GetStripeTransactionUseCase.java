package com.jexis.jexis_backend.stripe.application.useCases;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public class GetStripeTransactionUseCase {
    private final StripeClient client;

    public GetStripeTransactionUseCase(StripeClient client) {
        this.client = client;
    }

    public com.stripe.model.treasury.Transaction execute(String transactionId) {
        try {
            return client.v1().treasury().transactions().retrieve(transactionId);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve transaction", e);
        }
    }
}
