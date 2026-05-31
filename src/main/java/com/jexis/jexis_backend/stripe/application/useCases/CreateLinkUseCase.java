package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.core.AccountLink;

@Service
public class CreateLinkUseCase {
    private final StripeClient stripe;

    public CreateLinkUseCase(StripeClient stripe) {
        this.stripe = stripe;
    }

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
                                                .setReturnUrl("https://example.com?accountId=" + accountId)
                                                .build())
                                .build())
                .build();

        com.stripe.model.v2.core.AccountLink accountLink = stripe.v2().core().accountLinks().create(params);
        return accountLink;
    }
}
