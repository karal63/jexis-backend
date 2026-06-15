package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.treasury.FinancialAccount;
import com.stripe.net.RequestOptions;
import com.stripe.param.treasury.FinancialAccountCreateParams;

@Service
public class CreateTreasuryAccount {

    private final StripeClient client;

    public CreateTreasuryAccount(StripeClient client) {
        this.client = client;
    }

    public void execute(String connectAccountId) throws StripeException {
        FinancialAccountCreateParams params = FinancialAccountCreateParams.builder()
                .addSupportedCurrency("usd")
                .setFeatures(
                        FinancialAccountCreateParams.Features.builder()
                                .setDepositInsurance(
                                        FinancialAccountCreateParams.Features.DepositInsurance.builder()
                                                .setRequested(true)
                                                .build())
                                .setFinancialAddresses(
                                        FinancialAccountCreateParams.Features.FinancialAddresses.builder()
                                                .setAba(
                                                        FinancialAccountCreateParams.Features.FinancialAddresses.Aba
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .build())
                                .setInboundTransfers(
                                        FinancialAccountCreateParams.Features.InboundTransfers.builder()
                                                .setAch(
                                                        FinancialAccountCreateParams.Features.InboundTransfers.Ach
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .build())
                                .setIntraStripeFlows(
                                        FinancialAccountCreateParams.Features.IntraStripeFlows.builder()
                                                .setRequested(true)
                                                .build())
                                .setOutboundPayments(
                                        FinancialAccountCreateParams.Features.OutboundPayments.builder()
                                                .setAch(
                                                        FinancialAccountCreateParams.Features.OutboundPayments.Ach
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .setUsDomesticWire(
                                                        FinancialAccountCreateParams.Features.OutboundPayments.UsDomesticWire
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .build())
                                .setOutboundTransfers(
                                        FinancialAccountCreateParams.Features.OutboundTransfers.builder()
                                                .setAch(
                                                        FinancialAccountCreateParams.Features.OutboundTransfers.Ach
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .setUsDomesticWire(
                                                        FinancialAccountCreateParams.Features.OutboundTransfers.UsDomesticWire
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .build())
                                .build())
                .putExtraParam("nickname", "Jexis Treasury Account")
                .putExtraParam("features[card_issuing][requested]", true)
                .build();

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(connectAccountId).build();
        // For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.

        FinancialAccount financialAccount = client.v1().treasury().financialAccounts().create(params, requestOptions);
    }
}
