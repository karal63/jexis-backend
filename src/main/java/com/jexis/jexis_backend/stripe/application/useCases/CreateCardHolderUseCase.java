package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.stripe.application.dto.CreateCardHolderDto;
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

    /**
     * Creates a new cardholder
     *
     * Accepts connected account id from controller, creates a new
     * cardholder, and returns the created cardholder.
     *
     * @param connectedAccountId the id of the connected account
     * 
     * @return the created cardholder
     */
    public Cardholder execute(CreateCardHolderDto dto) throws StripeException {
        logger.info("STRIPE", "Starting cardholder creation for account: " + dto.connectedAccountId());

        CardholderCreateParams params = CardholderCreateParams.builder()
                .setType(CardholderCreateParams.Type.INDIVIDUAL)
                .setName(dto.name())
                .setEmail(dto.email())
                .setPhoneNumber(dto.phoneNumber())
                .setBilling(
                        CardholderCreateParams.Billing.builder()
                                .setAddress(
                                        CardholderCreateParams.Billing.Address
                                                .builder()
                                                .setLine1(dto.addressLine1())
                                                .setCity(dto.city())
                                                .setState(dto.state())
                                                .setCountry(dto.country())
                                                .setPostalCode(dto.postalCode())
                                                .build())
                                .build())
                .build();

        RequestOptions options = RequestOptions.builder()
                .setStripeAccount(dto.connectedAccountId())
                .build();

        Cardholder cardholder = stripe.v1().issuing().cardholders().create(params, options);
        logger.info("STRIPE", "Cardholder created successfully with id: " + cardholder.getId());

        return cardholder;
    }
}
