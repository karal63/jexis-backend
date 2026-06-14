package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // /**
    // * Creates a Stripe card holder for the specified connected account.
    // *
    // * Endpoint: POST /create-card-holder
    // *
    // * @param body the request payload containing the connected account identifier
    // * @return a response with the created card holder identifier
    // * @throws StripeException if the Stripe API call fails
    // */
    // @PostMapping("/create-card-holder")
    // public ResponseEntity<String> createCardHolder(@RequestBody
    // CreateCardHolderDto body) throws StripeException {
    // Cardholder cardHolder = createCardHolderUseCase.execute(body);
    // return ResponseEntity.ok(cardHolder.getId());
    // }

}
