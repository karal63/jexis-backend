package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.param.AccountCreateParams;

/**
 * CreateConnectUseCase
 *
 * This service class implements the use case for creating a new connect
 * account.
 * It contains only the business logic related to account creation
 *
 * Author: Leo
 */
@Service
public class CreateConnectUseCase {
    private final StripeClient stripe;
    private final AsyncLogger logger;

    public CreateConnectUseCase(StripeClient stripe, AsyncLogger logger) {
        this.stripe = stripe;
        this.logger = logger;
    }

    /**
     * Creates a new connect account
     *
     * Accepts account email from controller, creates a new connect account, and
     * returns the created account.
     *
     * @param email the email of the account to create
     *
     * @return the created account
     */
    public Account execute(String email) throws StripeException {
        logger.info("STRIPE", "Starting connect account creation for email: " +
                email);

        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.CUSTOM)
                .setCountry("US")
                .setEmail(email)
                .setBusinessType(AccountCreateParams.BusinessType.COMPANY)
                .setCapabilities(
                        AccountCreateParams.Capabilities.builder()
                                .setTransfers(
                                        AccountCreateParams.Capabilities.Transfers
                                                .builder()
                                                .setRequested(true)
                                                .build())
                                .build())
                .build();

        Account account = stripe.v1().accounts().create(params);
        logger.info("STRIPE", "Connect account created successfully with id: " +
                account.getId());

        return account;
    }
}
