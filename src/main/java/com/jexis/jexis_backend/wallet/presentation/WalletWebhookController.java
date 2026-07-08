package com.jexis.jexis_backend.wallet.presentation;

import com.jexis.jexis_backend.account.application.useCases.EditAccountUseCase;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/wallets")
public class WalletWebhookController {
    @Value("${stripe.webhook.secret.wallet}")
    private String webhookSecret;
    private final AsyncLogger logger;

    public WalletWebhookController(AsyncLogger logger) {
        this.logger = logger;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }


        switch(event.getType()) {
            case "treasury.received_credit.created":
                logger.info("STRIPE_WEBHOOK", "Received treasury credit created");
                break;
        }

        return ResponseEntity.ok("success");
    }
}
