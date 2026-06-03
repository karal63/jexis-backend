package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Cardholder;
import com.stripe.net.RequestOptions;
import com.stripe.param.issuing.CardholderCreateParams;

/**
 * CreateCardHolderUseCase
 *
 * This service class implements the use case for creating a new cardholder.
 * It contains only the business logic related to cardholder creation, such as
 * validating input data and interacting with the repository to persist the new
 * cardholder.
 *
 * Author: Leo
 */
@Service
public class CreateCardHolderUseCase {
        private final StripeClient stripe;
        private final AsyncLogger logger;

        public CreateCardHolderUseCase(StripeClient stripe, AsyncLogger logger) {
                this.stripe = stripe;
                this.logger = logger;
        }

        /*
         * Creates a new cardholder
         *
         * Accepts connected account id from controller, creates a new
         * cardholder, and returns the created cardholder.
         *
         * @param connectedAccountId the id of the connected account
         * 
         * @return the created cardholder
         */
        public Cardholder execute(String connectedAccountId) throws StripeException {
                logger.info("STRIPE", "Starting cardholder creation for account: " + connectedAccountId);

                CardholderCreateParams params = CardholderCreateParams.builder()
                                .setType(CardholderCreateParams.Type.INDIVIDUAL)
                                .setName("Jenny Rosen")
                                .setEmail("jenny.rosen@example.com")
                                .setPhoneNumber("+18888675309")
                                .setBilling(
                                                CardholderCreateParams.Billing.builder()
                                                                .setAddress(
                                                                                CardholderCreateParams.Billing.Address
                                                                                                .builder()
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

                Cardholder cardholder = stripe.v1().issuing().cardholders().create(params, options);
                logger.info("STRIPE", "Cardholder created successfully with id: " + cardholder.getId());

                return cardholder;
        }
}
