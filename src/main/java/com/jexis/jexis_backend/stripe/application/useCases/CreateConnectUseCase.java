package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.core.Account;
import com.stripe.param.v2.core.AccountCreateParams;

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

    /*
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
        logger.info("STRIPE", "Starting connect account creation for email: " + email);

        AccountCreateParams params = AccountCreateParams.builder()
                .setContactEmail(email)
                .setDisplayName(email)
                .setIdentity(
                        AccountCreateParams.Identity.builder()
                                .setCountry("US")
                                .setEntityType(AccountCreateParams.Identity.EntityType.COMPANY)
                                .build())
                .setConfiguration(
                        AccountCreateParams.Configuration.builder()
                                .setRecipient(
                                        AccountCreateParams.Configuration.Recipient
                                                .builder()
                                                .setCapabilities(
                                                        AccountCreateParams.Configuration.Recipient.Capabilities
                                                                .builder()
                                                                .setStripeBalance(
                                                                        AccountCreateParams.Configuration.Recipient.Capabilities.StripeBalance
                                                                                .builder()
                                                                                .setStripeTransfers(
                                                                                        AccountCreateParams.Configuration.Recipient.Capabilities.StripeBalance.StripeTransfers
                                                                                                .builder()
                                                                                                .setRequested(true)
                                                                                                .build())
                                                                                .build())
                                                                .build())
                                                .build())
                                .build())
                .setDefaults(
                        AccountCreateParams.Defaults.builder()
                                .setResponsibilities(
                                        AccountCreateParams.Defaults.Responsibilities
                                                .builder()
                                                .setFeesCollector(
                                                        AccountCreateParams.Defaults.Responsibilities.FeesCollector.APPLICATION)
                                                .setLossesCollector(
                                                        AccountCreateParams.Defaults.Responsibilities.LossesCollector.APPLICATION)
                                                .build())
                                .build())
                .setDashboard(AccountCreateParams.Dashboard.EXPRESS)
                .build();

        Account account = stripe.v2().core().accounts().create(params);
        logger.info("STRIPE", "Connect account created successfully with id: " + account.getId());

        return account;
    }
}
