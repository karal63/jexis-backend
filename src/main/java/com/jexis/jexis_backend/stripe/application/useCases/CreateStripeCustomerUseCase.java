package com.jexis.jexis_backend.stripe.application.useCases;

import com.jexis.jexis_backend.account.domain.entities.Account;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStripeCustomerUseCase {
    private final StripeClient client;

    public Customer execute(Account account) {
        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                            .setEmail(account.getEmail())
                            .setName(account.getFirstName() + " " + account.getLastName())
                    .build();

            return client.v1().customers().create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe customer", e);
        }
    }
}
