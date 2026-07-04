package com.jexis.jexis_backend.account.presentation;

import com.jexis.jexis_backend.account.application.useCases.EditAccountUseCase;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/accounts")
public class AccountWebhookController {
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    private final EditAccountUseCase editAccountUseCase;

    public AccountWebhookController(EditAccountUseCase editAccountUseCase) {
        this.editAccountUseCase = editAccountUseCase;
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
            case "account.updated":
                editAccountUseCase.execute(event.getAccount());
                break;
        }

        return ResponseEntity.ok("success");
    }
}
