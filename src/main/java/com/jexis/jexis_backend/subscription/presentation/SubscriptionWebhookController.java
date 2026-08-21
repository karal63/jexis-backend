package com.jexis.jexis_backend.subscription.presentation;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.subscription.application.useCases.CreateSubscriptionUseCase;
import com.jexis.jexis_backend.transaction.application.useCases.CreateCardTransactionUseCase;
import com.jexis.jexis_backend.transaction.application.useCases.UpdateCardTransactionUseCase;
import com.stripe.model.Event;
import com.stripe.model.Subscription;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/subscriptions")
@RequiredArgsConstructor
public class SubscriptionWebhookController {
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    @Value("${stripe.webhook.secret.subscription}")
    private String webhookSecret;
    private final AsyncLogger logger;

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, this.webhookSecret);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("invalid signature");
        }

        switch (event.getType()) {
            case "customer.subscription.created":
                Subscription subscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.created event for subscription ID: " + subscription.getId());
                createSubscriptionUseCase.execute(subscription);

                break;
        }
        return ResponseEntity.ok("success");
    }
}
