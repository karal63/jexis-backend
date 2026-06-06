package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jexis.jexis_backend.stripe.application.dto.ConnectResponse;
import com.jexis.jexis_backend.stripe.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateConnectDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateLinkDto;
import com.jexis.jexis_backend.stripe.application.dto.LinkResult;
import com.jexis.jexis_backend.stripe.application.useCases.CreateCardHolderUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateLinkUseCase;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Cardholder;
import com.stripe.model.v2.core.Account;
import com.stripe.model.v2.core.AccountLink;
import com.stripe.net.RequestOptions;

/**
 * StripeController
 *
 * REST controller in the presentation layer responsible for exposing
 * stripe-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated stripe use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@Controller
@RequestMapping("")
public class StripeController {
    private final CreateCardHolderUseCase createCardHolderUseCase;

    public StripeController(CreateConnectUseCase createConnectUseCase, CreateLinkUseCase createLinkUseCase,
            CreateCardHolderUseCase createCardHolderUseCase) {
        this.createCardHolderUseCase = createCardHolderUseCase;
    }

    /**
     * Creates a Stripe card holder for the specified connected account.
     *
     * Endpoint: POST /create-card-holder
     *
     * @param body the request payload containing the connected account identifier
     * @return a response with the created card holder identifier
     * @throws StripeException if the Stripe API call fails
     */
    @PostMapping("/create-card-holder")
    public ResponseEntity<String> createCardHolder(@RequestBody CreateCardHolderDto body) throws StripeException {
        Cardholder cardHolder = createCardHolderUseCase.execute(body.connectedAccountId());
        return ResponseEntity.ok(cardHolder.getId());
    }

}
