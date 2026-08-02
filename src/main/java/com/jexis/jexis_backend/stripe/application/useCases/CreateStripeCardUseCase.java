package com.jexis.jexis_backend.stripe.application.useCases;

import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.model.issuing.Card;
import com.stripe.net.RequestOptions;
import com.stripe.param.issuing.CardCreateParams;
import com.jexis.jexis_backend.card.domain.enums.CardReplacementReason;

@Service
public class CreateStripeCardUseCase {
    private final StripeClient client;

    public CreateStripeCardUseCase(StripeClient client) {
        this.client = client;
    }

    public Card execute(String cardholderId, String financialAccountId, String connectedAccountId) {
        return execute(cardholderId, financialAccountId, connectedAccountId, null, null);
    }

    public Card execute(String cardholderId, String financialAccountId, String connectedAccountId, String replacementFor,
            CardReplacementReason replacementReason) {
        try {
            CardCreateParams.Builder builder = CardCreateParams.builder()
                    .setCardholder(cardholderId)
                    .setFinancialAccount(financialAccountId)
                    .setCurrency("usd")
                    .setType(CardCreateParams.Type.VIRTUAL)
                    .setStatus(CardCreateParams.Status.ACTIVE);

            if (replacementFor != null) {
                builder.setReplacementFor(replacementFor);
            }

            if (replacementReason != null) {
                builder.setReplacementReason(mapReplacementReason(replacementReason));
            }

            CardCreateParams params = builder.build();

            RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(connectedAccountId).build();

            Card card = client.v1().issuing().cards().create(params, requestOptions);
            return card;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private CardCreateParams.ReplacementReason mapReplacementReason(CardReplacementReason replacementReason) {
        return switch (replacementReason) {
            case damaged -> CardCreateParams.ReplacementReason.DAMAGED;
            case expired -> CardCreateParams.ReplacementReason.EXPIRED;
            case lost -> CardCreateParams.ReplacementReason.LOST;
            case stolen -> CardCreateParams.ReplacementReason.STOLEN;
        };
    }
}
