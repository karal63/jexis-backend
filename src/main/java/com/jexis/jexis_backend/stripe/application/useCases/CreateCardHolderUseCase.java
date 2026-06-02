package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Cardholder;
import com.stripe.net.RequestOptions;
import com.stripe.param.issuing.CardholderCreateParams;

@Service
public class CreateCardHolderUseCase {
    private final StripeClient stripe;

    public CreateCardHolderUseCase(StripeClient stripe) {
        this.stripe = stripe;
    }

    public Cardholder execute(String connectedAccountId) throws StripeException {
        CardholderCreateParams params = CardholderCreateParams.builder()
                .setType(CardholderCreateParams.Type.INDIVIDUAL)
                .setName("Jenny Rosen")
                .setEmail("jenny.rosen@example.com")
                .setPhoneNumber("+18888675309")
                .setBilling(
                        CardholderCreateParams.Billing.builder()
                                .setAddress(
                                        CardholderCreateParams.Billing.Address.builder()
                                                .setLine1("1234 Main Street")
                                                .setCity("San Francisco")
                                                .setState("CA")
                                                .setCountry("US")
                                                .setPostalCode("94111")
                                                .build())
                                .build())
                .build();

        RequestOptions options = RequestOptions.builder()
                .setStripeAccount(connectedAccountId)
                .build();

        return stripe.v1().issuing().cardholders().create(params, options);
    }
}
