package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.treasury.FinancialAccount;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountUpdateParams;
import com.stripe.param.treasury.FinancialAccountUpdateParams;

@Service
public class SetupIssuingUseCase {
    private final StripeClient stripe;

    public SetupIssuingUseCase(StripeClient stripe) {
        this.stripe = stripe;
    }

    public void execute(String connectAccountId) throws StripeException {
        AccountUpdateParams params = AccountUpdateParams.builder()
                .setCapabilities(
                        AccountUpdateParams.Capabilities.builder()
                                .setCardIssuing(
                                        AccountUpdateParams.Capabilities.CardIssuing.builder()
                                                .setRequested(true)
                                                .build())
                                .setTransfers(
                                        AccountUpdateParams.Capabilities.Transfers.builder().setRequested(true).build())
                                .build())
                .putExtraParam("capabilities[treasury][requested]", true)
                .build();

        // For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.
        Account account = stripe.v1().accounts().update(connectAccountId, params);
    }
}