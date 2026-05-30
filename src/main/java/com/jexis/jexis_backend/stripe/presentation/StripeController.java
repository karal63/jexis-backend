package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jexis.jexis_backend.stripe.application.dto.CreateConnectDto;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.core.Account;

@Controller
@RequestMapping("")
public class StripeController {
    private final CreateConnectUseCase createConnectUseCase;

    public StripeController(CreateConnectUseCase createConnectUseCase) {
        this.createConnectUseCase = createConnectUseCase;
    }

    @PostMapping("/create-connect-account")
    public ResponseEntity<String> createConnectAccount(@RequestBody CreateConnectDto body) {

        String email = body.email();

        try {
            Account account = createConnectUseCase.execute(email);
            return ResponseEntity.ok(account.getId());
        } catch (StripeException e) {
            System.out.println("Stripe API error: " + e);
            return ResponseEntity.status(500).body("Failed to create Stripe Connect account: " + e.getMessage());
        }
    }
}
