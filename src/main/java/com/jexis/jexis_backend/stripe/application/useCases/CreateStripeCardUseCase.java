package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Card;
import com.stripe.net.RequestOptions;
import com.stripe.param.issuing.CardCreateParams;

@Service
public class CreateStripeCardUseCase {
    private final StripeClient client;

    public CreateStripeCardUseCase(StripeClient client) {
        this.client = client;
    }

    public Card execute(String cardholderId, String financialAccountId, String connectedAccountId)
            throws StripeException {
        CardCreateParams params = CardCreateParams.builder()
                .setCardholder(cardholderId)
                .setFinancialAccount(financialAccountId)
                .setCurrency("usd")
                .setType(CardCreateParams.Type.VIRTUAL)
                .setStatus(CardCreateParams.Status.ACTIVE)
                .build();

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(connectedAccountId).build();
        // For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.

        Card card = client.v1().issuing().cards().create(params, requestOptions);
        return card;
    }
}
