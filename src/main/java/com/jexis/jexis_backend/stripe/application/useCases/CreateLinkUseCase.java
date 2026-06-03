package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.core.AccountLink;

/**
 * CreateLinkUseCase
 *
 * This service class implements the use case for creating a new account link.
 * It contains only the business logic related to account link creation
 *
 * Author: Leo
 */
@Service
public class CreateLinkUseCase {
        private final StripeClient stripe;

        public CreateLinkUseCase(StripeClient stripe) {
                this.stripe = stripe;
        }

        /*
         * Creates a new account link
         *
         * Accepts an account ID from the controller, creates a new account link,
         * and returns the created account link.
         *
         * @param accountId the ID of the account for which to create a link
         * 
         * @return the created account link
         */
        public AccountLink execute(String accountId) throws StripeException {
                com.stripe.param.v2.core.AccountLinkCreateParams params = com.stripe.param.v2.core.AccountLinkCreateParams
                                .builder()
                                .setAccount(accountId)
                                .setUseCase(
                                                com.stripe.param.v2.core.AccountLinkCreateParams.UseCase.builder()
                                                                .setType(
                                                                                com.stripe.param.v2.core.AccountLinkCreateParams.UseCase.Type.ACCOUNT_ONBOARDING)
                                                                .setAccountOnboarding(
                                                                                com.stripe.param.v2.core.AccountLinkCreateParams.UseCase.AccountOnboarding
                                                                                                .builder()
                                                                                                .addConfiguration(
                                                                                                                com.stripe.param.v2.core.AccountLinkCreateParams.UseCase.AccountOnboarding.Configuration.RECIPIENT)
                                                                                                .setRefreshUrl("https://example.com")
                                                                                                .setReturnUrl("https://example.com?accountId="
                                                                                                                + accountId)
                                                                                                .build())
                                                                .build())
                                .build();

                com.stripe.model.v2.core.AccountLink accountLink = stripe.v2().core().accountLinks().create(params);
                return accountLink;
        }
}
