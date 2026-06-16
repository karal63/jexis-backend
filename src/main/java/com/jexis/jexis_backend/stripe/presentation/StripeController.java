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
 * @Author: Leo
 */
@Controller
@RequestMapping("")
public class StripeController {

    public StripeController() {

    }
}
