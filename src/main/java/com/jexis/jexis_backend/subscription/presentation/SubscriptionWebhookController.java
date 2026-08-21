package com.jexis.jexis_backend.subscription.presentation;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.subscription.application.useCases.CancelSubscriptionUseCase;
import com.jexis.jexis_backend.subscription.application.useCases.PauseOrResumeSubscriptionUseCase;
import com.jexis.jexis_backend.subscription.application.useCases.SyncSubscriptionUseCase;
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
    private final SyncSubscriptionUseCase syncSubscriptionUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;
    private final PauseOrResumeSubscriptionUseCase pauseOrResumeSubscriptionUseCase;
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
                Subscription createdSubscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.created event for subscription ID: " + createdSubscription.getId());
                syncSubscriptionUseCase.execute(createdSubscription);

                break;

            case "customer.subscription.updated":
                Subscription updatedSubscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.updated event for subscription ID: " + updatedSubscription.getId());
                syncSubscriptionUseCase.execute(updatedSubscription);

                break;

            case "customer.subscription.deleted":
                Subscription deletedSubscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.deleted event for subscription ID: " + deletedSubscription.getId());
                cancelSubscriptionUseCase.execute(deletedSubscription);

                break;

            case "customer.subscription.paused":
                Subscription pausedSubscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.paused event for subscription ID: " + pausedSubscription.getId());
                pauseOrResumeSubscriptionUseCase.execute(pausedSubscription);

                break;

            case "customer.subscription.resumed":
                Subscription resumedSubscription = (Subscription) event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                logger.info("STRIPE_WEBHOOK", "Received subscription.resumed event for subscription ID: " + resumedSubscription.getId());
                pauseOrResumeSubscriptionUseCase.execute(resumedSubscription);

                break;
        }
        return ResponseEntity.ok("success");
    }
}
